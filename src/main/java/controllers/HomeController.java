package controllers;

import cuenta.Cuenta;
import cuenta.RepoCuentas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController {
  public ModelAndView getHome(Request request, Response response) {
    String usuario = request.session().attribute("logged_user");
    if (usuario == null) {
      response.redirect("/signin");
    }
    Cuenta cuenta = RepoCuentas.getInstance().accountByUsername(usuario);
    return new ModelAndView(cuenta, cuenta.getTemplate());
  }
}
