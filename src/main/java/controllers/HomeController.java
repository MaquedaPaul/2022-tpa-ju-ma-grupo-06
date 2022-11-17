package controllers;

import cuenta.Cuenta;
import global.Unidad;
import organizacion.periodo.PeriodoMensual;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class HomeController {
  public ModelAndView getHome(Request request, Response response) {
    Cuenta cuenta = request.session().attribute("cuenta");
    if (cuenta == null) {
      response.redirect("/signin");
      return null;
    }
    PeriodoMensual periodo = new PeriodoMensual(LocalDate.of(2020,11,15));
    Map<String,Object> model = new HashMap<>();
    model.put("totalMensual",2500D);
    model.put("totalAnual",250700D);
    model.put("unidad", Unidad.LTS);
    model.put("sector","BUENOS AIRES");
    model.put("usuario","007");
    model.put("periodoActual",periodo);

    cuenta.guardarEnSesion(request);
    return new ModelAndView(model, cuenta.home());
  }
}
