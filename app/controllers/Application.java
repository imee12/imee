package controllers;

import models.User;
import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

	public static Result index() {
		User user = new User();
		user.setFirstName("imee");
		user.setEmail("imee@gmail.com");
		user.setLastName("cuison");
		user.insert();
		
		return ok(index.render("Your new application is ready."));
	}

}