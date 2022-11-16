package controllers;

import cuenta.Cuenta;
import cuenta.RepoCuentas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class HomeController {
  public ModelAndView getHome(Request request, Response response) {
    String usuario = request.session().attribute("logged_user");
    if (usuario == null) {
      response.redirect("/signin");
      return null;
    }
    Cuenta cuenta = RepoCuentas.getInstance().accountByUsername(usuario);
    HashMap<String, Object> model = new HashMap<String, Object>();
    model.put("cuenta", cuenta);
    return new ModelAndView(model, cuenta.getTemplate());
  }
}
