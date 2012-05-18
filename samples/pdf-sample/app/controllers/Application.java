package controllers;

import play.*;
import play.mvc.*;

import util.pdf.PDF;
import views.html.*;

public class Application extends Controller {

	public static Result index() {
		return ok(index.render("Your new application is ready."));
	}

	public static Result document() {
		return PDF.ok(document.render("Your new application is ready."));
	}

}