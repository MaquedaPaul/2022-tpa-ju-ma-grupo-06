package controllers.organizacion;

import cuenta.OrganizacionCuenta;
import organizacion.periodo.PeriodoAnual;
import repositorios.*;
import organizacion.Organizacion;
import spark.ModelAndView;
import spark.Request;
import spark.Response;


import java.time.LocalDate;
import java.util.*;

public class OrganizacionController {

  static Organizacion obtenerOrganizacion(Request request){
    OrganizacionCuenta usuario = request.session().attribute("cuenta");
    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario);
    return organizacion;
  }
  static String obtenerUsuario (Request request){
    OrganizacionCuenta cuenta = request.session().attribute("cuenta");
    return cuenta.getUsuario();
  }

  public static Map<String, Object> datosDelHome(Request request) {
    Map<String, Object> model = calcularHc(request);
    return model;

  }

  public static Map<String, Object> calcularHc(Request request){
    Map<String, Object> model = new HashMap<>();
    Organizacion organizacion = obtenerOrganizacion(request);
    int anioActual = LocalDate.now().getYear();
    double valor = organizacion.calcularHCTotal(new PeriodoAnual(LocalDate.of(anioActual,1, 15)));
    model.put("valorhc",valor);
    ImpactoController.getMiembrosImpactoOrg(request,model);
    request.session().attribute("valorHc", valor);
    return model;
  }

  public ModelAndView getCalculadoraHc(Request request, Response response) {
    return new ModelAndView(calcularHc(request), "organizacionHcTotalNew.hbs");
  }




}
