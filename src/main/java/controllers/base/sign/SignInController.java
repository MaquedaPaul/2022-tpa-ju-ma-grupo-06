package controllers.base.sign;

import cuenta.Cuenta;
import repositorios.RepoCuentas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignInController {
  private boolean comprobarSesionActiva(Request request){
    return request.session().attribute("cuenta") != null;
  }

  public ModelAndView getSignIn(Request request, Response response) {
    Map<String, Object> model = new HashMap<>();
    if(comprobarSesionActiva(request)){
      response.redirect("/home");
      return null;
    }

    if (request.cookie("UsuarioInexistente") != null) {
      model.put("usuarioInexistente", true);
    }

    return new ModelAndView(model, "signin.hbs");
  }

  public Response logIn(Request request, Response response) {
    String userQuery = request.queryParams("user");
    String userQueryPassword = request.queryParams("password");
    System.out.println("ingreso: " + userQuery + userQueryPassword);
    Cuenta cuenta = RepoCuentas.getInstance().accountByUsername(userQuery);

    if (cuenta == null || !Objects.equals(userQueryPassword, cuenta.getPassword())) {
      System.out.println("ingreso: " + userQuery + userQueryPassword);
      response.cookie("UsuarioInexistente", "something");
      response.redirect("/signin");
      return response;
    }

    cuenta.guardarEnSesion(request);
    response.redirect("/home");
    return response;
  }
}
