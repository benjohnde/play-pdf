# play-pdf

This module helps generating PDF documents dynamically from your Play! web application.
It simply renders your HTML- and CSS-based templates to PDF.
It is based on the Flying Saucer library, which in turn uses iText for PDF generation.

## Usage

I created an example application https://github.com/benjohnde/play-pdf/tree/master/example.

You can use a standard Play! scala template like this one:
```html
@(message: String)

@main("Welcome to Play 2.6.2") {
    Image: <img src="/public/images/favicon.png"/><br/>
    Hello world! <br/>
    @message <br/>
}
```

Then this template, after having imported `de.benjohn.play.pdf.PDF`, can simply be rendered as:

```java
public static Result document() {
    return PDF.ok(document.render("Your new application is ready."));
}
```  
where ```PDF.ok``` is a simple shorthand notation for:
```java
public static Result document() {
    return ok(PDF.toBytes(document.render("Your new application is ready."))).as("application/pdf")
}
```

## Template rules

Templates must generate XHTML.

If the template is using an image, stylesheet, etc., it usually is loaded via an http call.
The PDF modules tries to optimize that resource loading:
If you specify the URI as a path into the classpath of your Play! app, the resource is loaded directly instead.
See the above sample template for an example.

Of course you can link to CSS files in your class path also, but be aware not to
use the `media="screen"` qualifier.

Fonts you use must be explicitely packaged with your app.

```html
<html>
    <head>
        <style>
            body { font-family: FreeSans; }
        </style>
    </head>
    <body>
    ...
    </body>
</html>
```

The module imports font resources as specified in your [application configuration](https://github.com/benjohnde/play-pdf/blob/master/example/conf/application.conf).

Specify fonts as follows:

```bash
pdf.fonts = ["Arial.ttf", "Helvetica.ttf"]
```

Each font file should be placed in your Play app configuration folder, so that they will be included.
See also the example project.

## Installation

Currently the module is not hosted anywhere. In order to use it, you need to publish it locally in the current play-maven-repository. Therefore:

```bash
git clone https://github.com/benjohnde/play-pdf.git
cd play-pdf/module
sbt publish-local
```

Then, add to your libraryDependencies in your `build.sbt`:

```scala
libraryDependencies ++= Seq(
    "de.benjohn.play" %% "pdf" % "1.1.3"
)
```

After the next restart of the `sbt`, the module should be available.
If you are using an IDE like Eclipse, remember to re-generate your project files.

## Kudos

- [joergviola](https://github.com/joergviola) initial creator of this project
