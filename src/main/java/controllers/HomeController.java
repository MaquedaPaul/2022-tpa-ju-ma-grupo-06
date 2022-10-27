package controllers;

import miembro.Miembro;
import organizacion.TipoDocumento;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;

public class HomeController {
  public ModelAndView getHome(Request request, Response response) {
    String tipoUsuario = request.session().attribute("typo-usuario");
    Miembro miembro = new Miembro("juan", "juan", TipoDocumento.DNI, 1, new ArrayList<>());
    return new ModelAndView(miembro, "miembro.hbs");
  }

  public ModelAndView getTrayectos(Request request, Response response) {
    return null;
  }

  public ModelAndView getRegistro(Request request, Response response) {
    return null;
  }
}
