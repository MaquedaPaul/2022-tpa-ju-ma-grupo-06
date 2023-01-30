package controllers.base;

import cuenta.Cuenta;
import spark.Request;
import spark.Response;

public class Validador {

  public static void validarAcceso(Request request, Response response) {
    String url1 = request.url();
    System.out.println("RUTA ACCEDIDA: " + url1);
    if (request.url().endsWith("/signin") || request.url().endsWith("/signout")) {
      System.out.println("ACCEDI AL SIGNIN");
      return;
    }

    if (request.url().endsWith("/favicon.ico")) {
      System.out.println("IGNORO EL FAVICON");
      return;
    }

    if (request.session().attribute("cuenta") == null) {
      System.out.println("NO ESTABA LOGUEADO");
      System.out.println("REDIRECCIONO AL SIGNIN");
      response.redirect("/signin");
      return;
    }

    System.out.println("ESTO LOGUEADO");
    Cuenta cuenta = request.session().attribute("cuenta");

    if (!cuenta.puedeAccederA(request.url())) {
      System.out.println("NO PUEDO ACCEDER");
      System.out.println("REDIRECCIONO AL NOT FOUND");
      response.type("text/html");
      response.redirect("/not-found");
      return;
    }
    System.out.println("PUEDO ACCEDER");

  }
}
