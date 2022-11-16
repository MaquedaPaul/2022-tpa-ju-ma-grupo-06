package controllers;

import cuenta.Cuenta;
import cuenta.RepoCuentas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class SignInController {
  public ModelAndView getSignIn(Request request, Response response) {
    return new ModelAndView(null, "signin.hbs");
  }

  public ModelAndView logIn(Request request, Response response) {
    String userQuery = request.queryParams("user");
    String userQueryPassword = request.queryParams("password");
    Cuenta cuenta = RepoCuentas.getInstance().accountByUsername(userQuery);
    if (cuenta == null || !Objects.equals(userQueryPassword, cuenta.getPassword())) {
      response.redirect("/signin");
      return null;
    }
    String usuario = cuenta.getUsuario();

    String tipoCuenta = cuenta.tipoCuenta();
    request.session().attribute("logged_user", usuario);
    request.session().attribute("user-type", tipoCuenta);
    response.redirect("/home");
    return null;
  }
}
