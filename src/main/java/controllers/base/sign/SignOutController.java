package controllers.base.sign;

import cuenta.Cuenta;
import spark.Request;
import spark.Response;

public class SignOutController {
  public Response logOut(Request request, Response response) {
    Cuenta cuenta = request.session().attribute("cuenta");

    if (cuenta == null) {
      response.redirect("/signin");
      return response;
    }

    cuenta.limpiarSession(request);
    response.redirect("/signin");
    return response;
  }
}
