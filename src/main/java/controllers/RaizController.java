package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class RaizController {
  public ModelAndView getPage(Request request, Response response) {
    return new ModelAndView(null, "raiz.hbs");
  }
}
