package server;

import controllers.RaizController;
import controllers.SignInController;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Routes {
  public static void init() {
    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
    Spark.staticFiles.location("static");
    Spark.get("/", (request, response) -> new RaizController().getPage(request, response), engine);
    Spark.get("/signin", (request, response) -> new SignInController().getPage(request, response), engine);
  }
}
