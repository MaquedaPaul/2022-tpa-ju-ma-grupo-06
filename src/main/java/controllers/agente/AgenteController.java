package controllers.agente;

import cuenta.AgenteCuenta;

import global.Unidad;
import organizacion.Organizacion;

import organizacion.TipoOrganizacion;

import organizacion.periodo.PeriodoAnual;
import organizacion.periodo.PeriodoMensual;
import repositorios.RepoOrganizacion;
import reporte.ReporteOrganizaciones;
import reporte.ReporteSectorTerritorial;
import reporte.itemsreportes.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import territorio.AgenteTerritorial;
import territorio.SectorTerritorial;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AgenteController {

  static final DateTimeFormatter formatoFecha = new DateTimeFormatterBuilder()
        .appendPattern("yyyy-MM")
        .parseDefaulting(ChronoField.DAY_OF_MONTH, 15)
        .toFormatter();

  static final ReporteSectorTerritorial genReporte = new ReporteSectorTerritorial();

  static PeriodoMensual getPeriodoInicio(Request request) {
    return new PeriodoMensual(LocalDate.parse(request.queryParams("inicio"),formatoFecha));
  }

  static PeriodoMensual getPeriodoFin(Request request) {
    return new PeriodoMensual(LocalDate.parse(request.queryParams("fin"),formatoFecha));
  }

  //RUTAS


  public ModelAndView getHcTotal(Request request,Response response) {

    AgenteCuenta cuenta = request.session().attribute("cuenta");
    AgenteTerritorial agente = request.session().attribute("agente");
    SectorTerritorial sector = agente.getSectorTerritorial();

    PeriodoMensual periodo = new PeriodoMensual(LocalDate.now());
    PeriodoAnual periodoAnual = new PeriodoAnual(LocalDate.now());
    Map<String,Object> model = new HashMap<>();

    //
    model.put("totalMensual",sector.calcularHC(periodo));
    //
    model.put("totalAnual",sector.calcularHC(periodoAnual));

    model.put("unidad", "");
    model.put("sector",sector);
    model.put("usuario",cuenta.getUsuario());
    model.put("periodoActual",periodo);

    return new ModelAndView(model,"agenteHome.hbs");
  }



}