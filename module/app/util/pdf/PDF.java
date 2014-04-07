package util.pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import nu.validator.htmlparser.dom.HtmlDocumentBuilder;

import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.ITextUserAgent;
import org.xhtmlrenderer.resource.CSSResource;
import org.xhtmlrenderer.resource.ImageResource;
import org.xhtmlrenderer.resource.XMLResource;

import play.Logger;
import play.api.Play;
import play.api.templates.Html;
import play.mvc.Result;
import play.mvc.Results;
import scala.Option;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BaseFont;

public class PDF {

	private static final String PLAY_DEFAULT_URL = "http://localhost:" + play.Play.application().configuration().getInt("http.port", 9000);

	public static class MyUserAgent extends ITextUserAgent {

		public MyUserAgent(ITextOutputDevice outputDevice) {
			super(outputDevice);
		}

		@Override
		public ImageResource getImageResource(String uri) {
			Option<InputStream> option = Play.current().resourceAsStream(uri);
			if (option.isDefined()) {
				InputStream stream = option.get();
				try {
					Image image = Image.getInstance(getData(stream));
					scaleToOutputResolution(image);
					return new ImageResource(uri, new ITextFSImage(image));
				} catch (Exception e) {
					Logger.error("fetching image " + uri, e);
					throw new RuntimeException(e);
				}
			} else {
				return super.getImageResource(uri);
			}
		}

		@Override
		public CSSResource getCSSResource(String uri) {
			try {
				// uri is in fact a complete URL
				String path = new URL(uri).getPath();
				Option<InputStream> option = Play.current().resourceAsStream(
						path);
				if (option.isDefined()) {
					return new CSSResource(option.get());
				} else {
					return super.getCSSResource(uri);
				}
			} catch (MalformedURLException e) {
				Logger.error("fetching css " + uri, e);
				throw new RuntimeException(e);
			}
		}

		@Override
		public byte[] getBinaryResource(String uri) {
			Option<InputStream> option = Play.current().resourceAsStream(uri);
			if (option.isDefined()) {
				InputStream stream = option.get();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				try {
					copy(stream, baos);
				} catch (IOException e) {
					Logger.error("fetching binary " + uri, e);
					throw new RuntimeException(e);
				}
				return baos.toByteArray();
			} else {
				return super.getBinaryResource(uri);
			}
		}

		@Override
		public XMLResource getXMLResource(String uri) {
			Option<InputStream> option = Play.current().resourceAsStream(uri);
			if (option.isDefined()) {
				return XMLResource.load(option.get());
			} else {
				return super.getXMLResource(uri);
			}
		}

		private void scaleToOutputResolution(Image image) {
			float factor = getSharedContext().getDotsPerPixel();
			image.scaleAbsolute(image.getPlainWidth() * factor,
					image.getPlainHeight() * factor);
		}

		private byte[] getData(InputStream stream) throws IOException {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			copy(stream, baos);
			return baos.toByteArray();
		}

		private void copy(InputStream stream, OutputStream os)
				throws IOException {
			byte[] buffer = new byte[1024];
			while (true) {
				int len = stream.read(buffer);
				os.write(buffer, 0, len);
				if (len < buffer.length)
					break;
			}
		}
	}

	public static Result ok(Html html) {
		byte[] pdf = toBytes(html.body());
		return Results.ok(pdf).as("application/pdf");
	}

	public static byte[] toBytes(Html html) {
		byte[] pdf = toBytes(html.body());
		return pdf;
	}

	public static byte[] toBytes(String string) {
		return toBytes(string, PLAY_DEFAULT_URL);
	}

	public static Result ok(Html html, String documentBaseURL) {
		byte[] pdf = toBytes(html.body(), documentBaseURL);
		return Results.ok(pdf).as("application/pdf");
	}

	public static byte[] toBytes(Html html, String documentBaseURL) {
		byte[] pdf = toBytes(html.body(), documentBaseURL);
		return pdf;
	}

	public static byte[] toBytes(String string, String documentBaseURL) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		toStream(string, os, documentBaseURL);
		return os.toByteArray();
	}

	public static void toStream(String string, OutputStream os) {
		toStream(string, os, PLAY_DEFAULT_URL);
	}

	public static void toStream(String string, OutputStream os, String documentBaseURL) {
		try {
			InputStream input = new ByteArrayInputStream(string.getBytes("UTF-8"));
			ITextRenderer renderer = new ITextRenderer();
			String fontDirectory = Play.current().path() + "/conf/resources/fonts";
			if (new File(fontDirectory).isDirectory()) {
				addFontDirectory(renderer.getFontResolver(), fontDirectory);
			}
			MyUserAgent myUserAgent = new MyUserAgent(
					renderer.getOutputDevice());
			myUserAgent.setSharedContext(renderer.getSharedContext());
			renderer.getSharedContext().setUserAgentCallback(myUserAgent);
			Document document = new HtmlDocumentBuilder().parse(input);
			renderer.setDocument(document, documentBaseURL);
			renderer.layout();
			renderer.createPDF(os);
		} catch (Exception e) {
			Logger.error("Creating document from template", e);
		}
	}

	private static void addFontDirectory(ITextFontResolver fontResolver,
			String directory) throws DocumentException, IOException {
		File dir = new File(directory);
		for (File file : dir.listFiles()) {
			fontResolver.addFont(file.getAbsolutePath(), BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
		}
	}

}
