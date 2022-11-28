package controllers;

import cuenta.Cuenta;
import repositorios.RepoCuentas;
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
    System.out.println("ingreso: " + userQuery + userQueryPassword);

    Cuenta cuenta = RepoCuentas.getInstance().accountByUsername(userQuery);

    if (cuenta == null || !Objects.equals(userQueryPassword, cuenta.getPassword())) {
      System.out.println("ingreso: " + userQuery + userQueryPassword);
      response.redirect("/signin");
      return null;
    }

    cuenta.guardarEnSesion(request);
    response.redirect("/home");
    return null;
  }
}
