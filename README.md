# Scala PDF module

This module help in generating PDF documents dynamically from your Play! web application.
It simply renders your HTML- and CSS-based templates to PDF.
It is based on the Flying Saucer library, which in turn uses iText for PDF generation.

## Usage

### Scala

var document = <body><label>Scala PDF</label</body>
var bytes = PDF.toStream(views.html.pdf.example())

### Play Framework 2

You can use a standard Play! scala template like this one:
``` html
@(message: String)

@main("Welcome to Play 2.0") {
    Image: <img src="/public/images/favicon.png"/><br/>
    Hello world! <br/>
    @message <br/>
}
```

Then this template, after having imported ```nl.rhinofly.play.PDF```, can simply be rendered as:
``` scala
def document = Action {
  val bytes = PDF.toBytes(views.html.pdf.example())
  Ok(bytes).as("application/pdf")
}
```

## Template rules


Templates must generate XHTML.

If the template is using an image, stylesheet, etc., it usually is loaded via an http call.
The PDF modules tries to optimize that resource loading:

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

The Arial font is not available within the JVM thus it's required to add the TrueType Font file.

```scala
PDF.addFont("path/to/Arial.ttf")
```

## Installation
```
val appDependencies = Seq(
      "nl.rhinofly" %% "play-pdf" % "0.7"
)

val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
    resolvers += "Rhinofly Internal Repository" at "http://maven-repository.rhinofly.net:8081/artifactory/libs-release-local"
  )
```


## License
Released under the MIT license; see the file LICENSE.

## Releases

<table>
  <tr>
    <td>0.7</td>
    <td>19.02.2015</td>
    <td>Java => Scala</td>
    <td></td>
  </tr>
  <tr>
    <td>0.6</td>
    <td>07.01.2015</td>
    <td>Play 2.3</td>
    <td></td>
  </tr>
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
