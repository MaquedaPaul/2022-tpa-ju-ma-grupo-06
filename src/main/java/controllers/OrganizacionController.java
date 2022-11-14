package controllers;

import cuenta.RepoCuentas;
import organizacion.Organizacion;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class OrganizacionController extends AccountController {

  public ModelAndView getPage(Request request, Response response) {
    String usuario = comprobarSession(request, response);
    comprobarTipoCuenta(request, response, "organizacion");
    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario).get(0);
    return new ModelAndView(organizacion.getSolicitudes(), "gestionarVinculaciones.hbs");
  }
}
