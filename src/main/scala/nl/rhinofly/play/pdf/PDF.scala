package nl.rhinofly.play.pdf

import com.lowagie.text.pdf.BaseFont
import java.io.{ ByteArrayOutputStream, StringReader, StringWriter }
import org.w3c.tidy.Tidy
import org.xhtmlrenderer.pdf.ITextRenderer
import org.xhtmlrenderer.resource.XMLResource

object PDF {

  private val renderer = new ITextRenderer
  private val userAgent = new PDFUserAgent(renderer.getOutputDevice)
  private val fontResolver = renderer.getFontResolver
  private val tidy = new Tidy

  renderer.getSharedContext.setUserAgentCallback(userAgent)

  def toBytes(body: String, documentBaseURL: String): Array[Byte] =
    toStream(tidify(body), documentBaseURL).toByteArray

  def toStream(body: String, documentBaseURL: String): ByteArrayOutputStream = {
    val output = new ByteArrayOutputStream
    val reader = new StringReader(body)
    val document = XMLResource.load(reader).getDocument
    renderer.setDocument(document, documentBaseURL)
    renderer.layout
    renderer.createPDF(output)
    output
  }

  def addFont(fontName: String): Unit =
    fontResolver.addFont(fontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED)

  private def tidify(body: String): String = {
    tidy.setXHTML(true)
    val writer = new StringWriter
    tidy.parse(new StringReader(body), writer)
    writer.getBuffer.toString
  }

}