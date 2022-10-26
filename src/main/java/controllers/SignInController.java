package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class SignInController {
  public ModelAndView getPage(Request request, Response response) {
    return new ModelAndView(null, "signin.hbs");
  }
}
