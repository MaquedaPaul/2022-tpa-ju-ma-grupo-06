package server.routers;

import controllers.base.ErrorController;
import controllers.base.HomeController;
import controllers.base.RaizController;
import controllers.base.RecomendacionController;
import controllers.base.sign.SignInController;
import controllers.base.sign.SignOutController;
import spark.Spark;
import spark.TemplateEngine;

public class RouterGeneral {
  public static void init(TemplateEngine engine) {
    RaizController raizController = new RaizController();
    ErrorController errorController = new ErrorController();
    HomeController homeController = new HomeController();
    RecomendacionController recomendacionController = new RecomendacionController();
    SignInController signInController = new SignInController();
    SignOutController signOutController = new SignOutController();

    Spark.get("/not-found", errorController::get404Page,engine);
    Spark.get("/", raizController::getPage, engine);
    Spark.get("/recomendaciones", recomendacionController::getRecomendaciones, engine);
    Spark.get("/home",homeController::getHome, engine);
    Spark.get("/signin",signInController::getSignIn, engine);
    Spark.post("/signin", signInController::logIn, engine);
    Spark.post("/signout", signOutController::logOut, engine);
  }
}
