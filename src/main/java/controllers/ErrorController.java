package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ErrorController {

  public ModelAndView get404Page(Request request, Response response) {
    return new ModelAndView(null, "notFound.hbs");
  }

}
