package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class AgenteController extends AccountController {
  public ModelAndView getCompocicionHc(Request request, Response response) {
    comprobarSession(request, response);
    comprobarTipoCuenta(request, response, "agente");
    return new ModelAndView(null, "agenteCompocicionHc.hbs");
  }

  public ModelAndView getEvolucionHc(Request request, Response response) {
    comprobarSession(request, response);
    comprobarTipoCuenta(request, response, "agente");
    return new ModelAndView(null, "agenteEvolucionHc.hbs");
  }

  public ModelAndView getHcTotal(Request request, Response response) {
    comprobarSession(request, response);
    comprobarTipoCuenta(request, response, "agente");
    return new ModelAndView(null, "agenteHcTotal.hbs");
  }

  public ModelAndView getEvolucionOrganizacion(Request request, Response response) {
    comprobarSession(request, response);
    comprobarTipoCuenta(request, response, "agente");
    return new ModelAndView(null, "agenteOrganizacion.hbs");
  }
}