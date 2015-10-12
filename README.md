This module is no longer supported. Anyone is invited to take over!
===================================================================

Play 2.0 PDF module
-------------------

This module helps generating PDF documents dynamically from your Play! web application.
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
			font-family: FreeSans;
		}
		--></style>	
	</head>
	<body>
		...
	</body>
</html>
```
Since the FreeSans font is not available to the java VM, you are required to
add the corresponding font file, "FreeSans.ttf" to your Play! app.
The module adds ```/conf/resources/fonts``` to the list of directories
searched for font files.

Installation
------------

Currently, the module is hosted at http://www.joergviola.de/releases/.
Therefore, including the following lines in your ```Build.scala``` will resolve it:
```
val appDependencies = Seq(
  ...
      "de.joergviola" %% "play-pdf" % "0.6-SNAPSHOT"
)
val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
  ...
  resolvers += Resolver.url("Violas Play Modules", url("http://www.joergviola.de/releases/"))(Resolver.ivyStylePatterns)
)
```
After the next restart of Play!, the module is available.
If you are using an IDE like Eclipse, remember to re-generate your project files. 


License
-------

Released under the MIT license; see the file LICENSE.

Releases
------------

<table>
	<tr>
		<td>0.5</td>
		<td>11.06.2013</td>
		<td>Fix with higher UTF-8 codes, documentBaseURL</td>
		<td>Thanks Wolfert de Kraker</td>
	</tr>
	<tr>
		<td>0.4</td>
		<td>08.02.2013</td>
		<td>Play 2.1</td>
		<td></td>
	</tr>
	<tr>
		<td>0.4</td>
		<td>04.02.2013</td>
		<td>Play 2.1.RC4, remote images</td>
		<td></td>
	</tr>
	<tr>
		<td>0.3</td>
		<td>15.06.2012</td>
		<td>CSS handling</td>
		<td></td>
	</tr>
	<tr>
		<td>0.2</td>
		<td>21.05.2012</td>
		<td>Font handling</td>
		<td></td>
	</tr>
	<tr>
		<td>0.1</td>
		<td>18.05.2012</td>
		<td>Initial release</td>
		<td></td>
	</tr>
</table>
