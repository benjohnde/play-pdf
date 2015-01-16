package nl.rhinofly.play.pdf

import com.lowagie.text.pdf.BaseFont
import java.io.{ ByteArrayOutputStream, OutputStream, StringReader, StringWriter }
import org.w3c.tidy.Tidy
import org.xhtmlrenderer.pdf.{ ITextFontResolver, ITextRenderer }
import org.xhtmlrenderer.resource.XMLResource
import play.twirl.api.Html
import scala.collection.JavaConversions.asScalaBuffer

object PDF {
  
  private val PLAY_DEFAULT_URL = "http://localhost:9000";

  def toBytes(html: Html): Array[Byte] = toBytes(tidify(html.body))
  def toBytes(body: String): Array[Byte] = toBytes(body, PLAY_DEFAULT_URL)
  def toBytes(html: Html, documentBaseURL: String): Array[Byte] = toBytes(tidify(html.body), documentBaseURL)

  def toBytes(body: String, documentBaseURL: String): Array[Byte] = {
    val output = new ByteArrayOutputStream
    toStream(body, output, documentBaseURL);
    return output.toByteArray();
  }

  def tidify(body: String): String = {
    val tidy = new Tidy
    tidy.setXHTML(true)
    val writer = new StringWriter
    tidy.parse(new StringReader(body), writer)
    writer.getBuffer.toString
  }

  def toStream(body: String, output: OutputStream): Unit = toStream(body, output, PLAY_DEFAULT_URL)
  def toStream(body: String, output: OutputStream, documentBaseURL: String): Unit = {
    val reader = new StringReader(body)
    val renderer = new ITextRenderer
    addFontsFromConfig(renderer.getFontResolver)
    val userAgent = new PDFUserAgent(renderer.getOutputDevice)
    userAgent.setSharedContext(renderer.getSharedContext)
    renderer.getSharedContext.setUserAgentCallback(userAgent)
    val document = XMLResource.load(reader).getDocument
    renderer.setDocument(document, documentBaseURL)
    renderer.layout
    renderer.createPDF(output)
  }

  def addFontsFromConfig(resolver: ITextFontResolver) = {
    val configuration = play.api.Play.current.configuration
    val fonts = configuration.getStringList("pdf.fonts")
    fonts.map(_.toList.map(resolver.addFont(_, BaseFont.IDENTITY_H, BaseFont.EMBEDDED)))
  }

}