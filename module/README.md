Play 2.0 PDF module
===================

This module help in generating PDF documents dynamically from your Play! web application.
It simply renders your HTML- and CSS-based templates to PDF.
It is based on the Flying Saucer library, which in turn uses iText for PDF generation.
   
Usage
-----

I have provided a simple example application at
https://github.com/joergviola/play20-pdf/samples/pdf-sample.

You can use a standard Play! scala template like this one:
``` html
@(message: String)

@main("Welcome to Play 2.0") {
    Image: <img src="/public/images/favicon.png"/><br/>
    Hello world! <br/>
    @message <br/>
}
```

Then this template, after having imported ```util.pdf.PDF```, can simply be rendered as:
``` java
	public static Result document() {
		return PDF.ok(document.render("Your new application is ready."));
	}
```  
where ```PDF.ok``` is a simple shorthand notation for:
``` java
	ok(PDF.toBytes(document.render("Your new application is ready."))).as("application/pdf")
```

Template rules
--------------

Templates must generate XHTML.

If the template is using an image, stylesheet, etc., it usually is loaded via an http call.
The PDF modules tries to optimize that resource loading:
If you specify the URI as a path into the classpath of your Play! app, the resource is loaded directly instead.
See the above sample template for an example.

Of course you can link to CSS files in your class path also, but be aware not to
use the ``` media="screen"```qualifier. 
  
Fonts you use must be explicitely packaged with your app.
```
<html>
	<head>
		<style type="text/css"><!--
		body {
			...
			font-family: Arial;
		}
		--></style>	
	</head>
	<body>
		...
	</body>
</html>
```
Since the Arial font is not available to the java VM, you are required to
add the corresponding font file, "Arial.ttf" to your Play! app.
The module adds ```/conf/fonts``` to the list of directories searched for font files.

Installation
------------

Currently, the module is hosted at http://www.joergviola.de/releases/.
Therefore, including the following lines in your ```Build.scala``` will resolve it:
```
val appDependencies = Seq(
  ...
  "pdf" % "pdf_2.9.1" % "0.2"
)
val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
  ...
  resolvers += Resolver.url("Violas Play Modules", url("http://www.joergviola.de/releases/"))(Resolver.ivyStylePatterns)
)
```
After the next restart of Play!, the module is available.
If you are using an IDE like Eclipse, remember to re-generate your project files. 


Releases
------------

<table>
	<tr>
		<td>0.2</td>
		<td>21.05.2012</td>
		<td>Font handling</td>
	</tr>
	<tr>
		<td>0.1</td>
		<td>18.05.2012</td>
		<td>Initial release</td>
	</tr>
</table>
