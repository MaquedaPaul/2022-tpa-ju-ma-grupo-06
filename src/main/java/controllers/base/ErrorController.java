package controllers.base;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ErrorController {

  public ModelAndView get404Page(Request request, Response response) {
    response.status(404);
    return new ModelAndView(null, "notFound.hbs");
  }

}
