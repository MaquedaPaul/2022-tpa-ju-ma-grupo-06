package controllers;

import organizacion.Organizacion;
import organizacion.repositorio.RepoOrganizacion;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.List;

public class MiembroController {
  public ModelAndView getTrayectos(Request request, Response response) {
    comprobarSession(request, response);
    comprobarTipoCuenta(request, response);
    return new ModelAndView(null,"trayectos.hbs");
  }

  public ModelAndView pedirVinculacion(Request request, Response response) {
    comprobarSession(request, response);
    comprobarTipoCuenta(request, response);
    return null;
  }

  public ModelAndView getRegistro(Request request, Response response) {
    comprobarSession(request, response);
    comprobarTipoCuenta(request, response);
    return new ModelAndView(null,"registro.hbs");
  }

  public ModelAndView getRegistrarTrayecto(Request request, Response response) {
    comprobarSession(request, response);
    comprobarTipoCuenta(request, response);
    return new ModelAndView(null,"registrarTrayecto.hbs");
  }

  public ModelAndView getVinculacion(Request request, Response response) {
    comprobarSession(request, response);
    comprobarTipoCuenta(request, response);
    List<Organizacion> organizaciones =  RepoOrganizacion.getInstance().getOrganizaciones();
    return new ModelAndView(organizaciones,"vinculacion.hbs");
  }

  public String comprobarSession(Request request, Response response) {
    String usuario = request.session().attribute("logged_user");
    String tipoCuenta = request.session().attribute("logged_user");
    if (usuario == null ) {
      response.redirect("/signin");
    }
    return usuario;
  }

  public void comprobarTipoCuenta(Request request,  Response response) {
    String tipoUsuario = request.session().attribute("user-type");
    if (!tipoUsuario.equals("miembro")) {
      response.redirect("/home");
    }
  }
}
