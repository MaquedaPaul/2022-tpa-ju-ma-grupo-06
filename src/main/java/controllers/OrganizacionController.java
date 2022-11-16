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
    return new ModelAndView(organizacion, "organizacionGestionarVinculaciones.hbs");
  }

  public ModelAndView getCalculadoraHc(Request request, Response response) {
    String usuario = comprobarSession(request, response);
    comprobarTipoCuenta(request, response, "organizacion");
    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario).get(0);
    return new ModelAndView(organizacion, "organizacionCalculadoraHc.hbs");
  }

  public ModelAndView getHcTotal(Request request, Response response) {
    String usuario = comprobarSession(request, response);
    comprobarTipoCuenta(request, response, "organizacion");
    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario).get(0);
    return new ModelAndView(organizacion, "organizacionHcTotal.hbs");
  }

  public ModelAndView getImpactoMiembro(Request request, Response response) {
    String usuario = comprobarSession(request, response);
    comprobarTipoCuenta(request, response, "organizacion");
    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario).get(0);
    return new ModelAndView(organizacion, "organizacionImpactoMiembro.hbs");
  }

  public ModelAndView getIndicadorHcSector(Request request, Response response) {
    String usuario = comprobarSession(request, response);
    comprobarTipoCuenta(request, response, "organizacion");
    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario).get(0);
    return new ModelAndView(organizacion, "organizacionIndicadorHcSector.hbs");
  }

  public ModelAndView getMediciones(Request request, Response response) {
    String usuario = comprobarSession(request, response);
    comprobarTipoCuenta(request, response, "organizacion");
    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario).get(0);
    return new ModelAndView(organizacion, "organizacionRegistrarMediciones.hbs");
  }

  public ModelAndView getMedicionesArchivo(Request request, Response response) {
    String usuario = comprobarSession(request, response);
    comprobarTipoCuenta(request, response, "organizacion");
    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario).get(0);
    return new ModelAndView(organizacion, "organizacionCargarArchivoMedicion.hbs");
  }

  public ModelAndView getMedicionesPerse(Request request, Response response) {
    String usuario = comprobarSession(request, response);
    comprobarTipoCuenta(request, response, "organizacion");
    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario).get(0);
    return new ModelAndView(organizacion, "organizacionCargarMedicion.hbs");
  }
}
