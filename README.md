Play 2.0 PDF module
===================

A simple module for generating PDF files from HTML templates.
Based upon [Flying Saucer](http://code.google.com/p/flying-saucer/).

As simple to use as
```
	public static Result document() {
		return PDF.ok(document.render("Your new application is ready."));
	}
```
