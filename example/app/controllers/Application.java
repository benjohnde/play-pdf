package controllers;

import play.*;
import play.mvc.*;

import de.benjohn.play.pdf.PDF;
import views.html.*;

public class Application extends Controller {
    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result document() {
        return PDF.ok(document.render("Your new application is ready."));
    }
}
