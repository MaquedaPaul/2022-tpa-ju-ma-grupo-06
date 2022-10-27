package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class SignOutController {
  public ModelAndView logOut(Request request, Response response) {
    request.session().attribute("logged_user", null);
    response.redirect("/signin");
    return null;
  }
}
