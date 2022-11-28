package controllers;

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

  private final DateTimeFormatter formatoFecha = new DateTimeFormatterBuilder()
        .appendPattern("yyyy-MM")
        .parseDefaulting(ChronoField.DAY_OF_MONTH, 15)
        .toFormatter();

  private final ReporteSectorTerritorial genReporte = new ReporteSectorTerritorial();

  private PeriodoMensual getPeriodoInicio(Request request) {
    return new PeriodoMensual(LocalDate.parse(request.queryParams("inicio"),formatoFecha));
  }

  private PeriodoMensual getPeriodoFin(Request request) {
    return new PeriodoMensual(LocalDate.parse(request.queryParams("fin"),formatoFecha));
  }

  //RUTAS

  public ModelAndView getComposicionHc(Request request, Response response) {

    AgenteCuenta cuentaUser = request.session().attribute("cuenta");

    return new ModelAndView(cuentaUser,"agenteComposicionHCConsulta.hbs");
  }

  public ModelAndView getComposicionHcGrafico(Request request, Response response) {
    AgenteCuenta agenteCuenta = request.session().attribute("cuenta");

    //OBTENER PERIODOS
    PeriodoMensual inicio = this.getPeriodoInicio(request);
    PeriodoMensual fin = this.getPeriodoFin(request);

    //VALIDAR PERIODOS
    if(inicio.esDespuesDe(fin.getFecha())) {
      response.redirect("/home/composicion-hc");
      return null;
    }

    AgenteTerritorial agente = request.session().attribute("agente");
    //OBTENER SECTOR
    SectorTerritorial sector = agente.getSectorTerritorial();
    List<ComposicionHcSectorTerritorial> items = genReporte.reporteComposicionHC(inicio, fin, sector);

    Map<String,Object> model = new HashMap<>();
    model.put("items",items);
    model.put("sector",sector);
    model.put("inicio",inicio);
    model.put("fin",fin);

    return new ModelAndView(model,"agenteComposicionHCRespuesta.hbs");
    //

    /*
    PeriodoMensual periodo = new PeriodoMensual(LocalDate.of(2020,7,15));
    PeriodoMensual periodo2 = new PeriodoMensual(LocalDate.of(2020,8,15));
    PeriodoMensual periodo3 = new PeriodoMensual(LocalDate.of(2020,9,15));


    List<ComposicionHcSectorTerritorial> items = new ArrayList<>();
    SectorTerritorial sector = new SectorTerritorial(null, TipoSectorTerritorial.PROVINCIA);
    ComposicionHcSectorTerritorial elem1 = new ComposicionHcSectorTerritorial(sector,200D,2000D,periodo);
    ComposicionHcSectorTerritorial elem2 = new ComposicionHcSectorTerritorial(sector,220D,2200D,periodo2);
    ComposicionHcSectorTerritorial elem3 = new ComposicionHcSectorTerritorial(sector,230D,2900D,periodo3);
    items.add(elem1);
    items.add(elem2);
    items.add(elem3);

    Map<String,Object> model = new HashMap<>();
    model.put("items",items);
    model.put("sector",sector);
    model.put("inicio",inicio);
    model.put("fin",fin);
    */

  }

  public ModelAndView getEvolucionHc(Request request, Response response) {
    AgenteCuenta agenteCuenta = request.session().attribute("cuenta");

    return new ModelAndView(agenteCuenta,"agenteEvolucionHcConsulta.hbs");
  }

  public ModelAndView getEvolucionHcGrafico(Request request, Response response) {
    AgenteCuenta agenteCuenta = request.session().attribute("cuenta");

    PeriodoMensual inicio = getPeriodoInicio(request);
    PeriodoMensual fin = getPeriodoFin(request);

    AgenteTerritorial agente = request.session().attribute("agente");
    SectorTerritorial sector = agente.getSectorTerritorial();
    List<EvolucionHCSectorTerritorial> evols = genReporte.reporteEvolucionHC(sector,inicio,fin);

    Map<String,Object> model = new HashMap<>();
    model.put("items",evols);
    model.put("sector",sector);
    model.put("inicio",inicio);
    model.put("fin",fin);
    model.put("usuario",agenteCuenta.getUsuario());
    return new ModelAndView(model,"agenteEvolucionHcRespuesta.hbs");
/*
    PeriodoMensual periodo = new PeriodoMensual(LocalDate.of(2020,7,15));
    PeriodoMensual periodo2 = new PeriodoMensual(LocalDate.of(2020,8,15));
    PeriodoMensual periodo3 = new PeriodoMensual(LocalDate.of(2020,9,15));

    List<EvolucionHCSectorTerritorial> evols = new ArrayList<>();
    SectorTerritorial sector = new SectorTerritorial(null, TipoSectorTerritorial.PROVINCIA);

    EvolucionHCSectorTerritorial evo1 = new EvolucionHCSectorTerritorial(sector, periodo, 2000D);
    EvolucionHCSectorTerritorial evo2 = new EvolucionHCSectorTerritorial(sector, periodo2, 3000D);
    EvolucionHCSectorTerritorial evo3 = new EvolucionHCSectorTerritorial(sector, periodo3, 2500D);

    evols.add(evo1);
    evols.add(evo2);
    evols.add(evo3);

    Map<String,Object> model = new HashMap<>();
    model.put("items",evols);
    model.put("sector",sector);
    model.put("inicio",inicio);
    model.put("fin",fin);
*/}

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
    model.put("unidad", "ESTO NI IDEA");
    model.put("sector",sector.getNombre());
    model.put("usuario",cuenta.getUsuario());
    model.put("periodoActual",periodo);

    return new ModelAndView(model,"agenteHome.hbs");
  }

  public ModelAndView getOrganizaciones(Request request,Response response) {

    AgenteCuenta cuenta = request.session().attribute("cuenta");
    AgenteTerritorial agente = request.session().attribute("agente");
    SectorTerritorial sector = agente.getSectorTerritorial();
    List<Organizacion> organizaciones = sector.getOrganizaciones();
    System.out.println(organizaciones.size());
    Map<String,Object> model = new HashMap<>();
    model.put("usuario",cuenta.getUsuario());
    model.put("organizaciones",organizaciones);
    /*
    Organizacion org1 = new Organizacion("PIRULO SA", TipoOrganizacion.EMPRESA,"","",null);
    Organizacion org2 = new Organizacion("SANGUCHITOS Y ASOCIADOS", TipoOrganizacion.ONG,"","",null);
    Organizacion org3 = new Organizacion("PIRULO PAYBACK SRL", TipoOrganizacion.INSTITUCION,"","",null);
    org1.setId(1L);
    org2.setId(2L);
    org3.setId(3L);
    List<Organizacion> organizaciones = new ArrayList<>();
    organizaciones.add(org1);
    organizaciones.add(org2);
    organizaciones.add(org3);
    Map<String,Object> model = new HashMap<>();
    model.put("usuario","JORGE");
    model.put("organizaciones",organizaciones);
*/
    return new ModelAndView(model,"agenteOrganizacion.hbs");
  }

  public ModelAndView getHcTipoOrg(Request request, Response response) {

    AgenteTerritorial agente = request.session().attribute("agente");
    AgenteCuenta cuenta = request.session().attribute("cuenta");
    PeriodoMensual periodo = new PeriodoMensual(LocalDate.now());
    PeriodoMensual periodo2 = new PeriodoMensual(LocalDate.now().plusDays(1L));
    HCPorTipoOrganizacion hcPorTipoOrganizacion = new ReporteOrganizaciones(RepoOrganizacion.getInstance())
        .hcPorTipoOrganizacion(periodo,periodo2)
        .stream()
        .filter(rep -> rep.getUnTipo() == TipoOrganizacion.valueOf(request.params("tipo"))).collect(Collectors.toList()).get(0);

    Map<String,Object> model = new HashMap<>();
    model.put("valorMensual",hcPorTipoOrganizacion.getHc());
    model.put("valorAnual",1000D);
    model.put("unidad", Unidad.LTS);
    model.put("sector",agente.getSectorTerritorial().getNombre());
    model.put("usuario",cuenta.getUsuario());
    model.put("periodoActual",periodo);
    model.put("tipo",hcPorTipoOrganizacion.getUnTipo());

    return new ModelAndView(model,"agenteHcTotalTipoOrg.hbs");
  }

  public ModelAndView getEvolucionHcOrg(Request request, Response response) {

    AgenteCuenta cuenta = request.session().attribute("cuenta");
    Long idOrg = Long.valueOf(request.params("id"));
    Organizacion org = RepoOrganizacion.getInstance().getOrganizacionById(idOrg);
    Map<String,Object> model = new HashMap<>();
    model.put("usuario",cuenta.getUsuario());
    model.put("nombreOrg",org.getRazonSocial());
    model.put("id",request.params("id"));

    return new ModelAndView(model,"agenteOrgEvolucionHCConsulta.hbs");
  }

  public ModelAndView getEvolucionHcOrgGrafico(Request request, Response response) {

    //OBTENER PERIODOS
    PeriodoMensual inicio = this.getPeriodoInicio(request);
    PeriodoMensual fin = this.getPeriodoFin(request);

    AgenteCuenta cuenta = request.session().attribute("cuenta");
    Long idOrg = Long.valueOf(request.params("id"));
    Organizacion org = RepoOrganizacion.getInstance().getOrganizacionById(idOrg);

    //VALIDAR PERIODOS
    if(inicio.esDespuesDe(fin.getFecha())) {
      response.redirect("/home/organizaciones");
      return null;
    }

    List<EvolucionHCOrganizacion> items = new ReporteOrganizaciones(RepoOrganizacion.getInstance()).EvolucionHCEntre(org,inicio,fin);

    Map<String,Object> model = new HashMap<>();
    model.put("usuario",cuenta.getUsuario());
    model.put("nombreOrg",org.getRazonSocial());
    model.put("id",org.getId());
    model.put("items",items);

    return new ModelAndView(model,"agenteOrgEvolucionHCRespuesta.hbs");
    /*
    PeriodoMensual periodo = new PeriodoMensual(LocalDate.of(2020,11,15));
    Organizacion org = new Organizacion();

    EvolucionHCOrganizacion evo1 = new EvolucionHCOrganizacion(org,periodo,2000D);
    EvolucionHCOrganizacion evo2 = new EvolucionHCOrganizacion(org,periodo,5000D);
    EvolucionHCOrganizacion evo3 = new EvolucionHCOrganizacion(org,periodo,4000D);
    items.add(evo1);
    items.add(evo2);
    items.add(evo3);

    Map<String,Object> model = new HashMap<>();
    model.put("usuario","007");
    model.put("nombreOrg","PAPELERA S.A");
    model.put("id",request.params("id"));
    model.put("items",items);
*/
  }

  public ModelAndView getComposicionHcOrg(Request request, Response response) {

    AgenteCuenta cuenta = request.session().attribute("cuenta");
    Long idOrg = Long.valueOf(request.params("id"));
    Organizacion org = RepoOrganizacion.getInstance().getOrganizacionById(idOrg);

    Map<String,Object> model = new HashMap<>();
    model.put("usuario",cuenta.getUsuario());
    model.put("nombreOrg",org.getRazonSocial());
    model.put("id",org.getId());

    return new ModelAndView(model,"agenteOrgComposicionHCConsulta.hbs");
  }

  public ModelAndView getComposicionHcOrgGrafico(Request request, Response response) {

    PeriodoMensual inicio = this.getPeriodoInicio(request);
    PeriodoMensual fin = this.getPeriodoFin(request);

    AgenteCuenta cuenta = request.session().attribute("cuenta");
    Long idOrg = Long.valueOf(request.params("id"));
    Organizacion org = RepoOrganizacion.getInstance().getOrganizacionById(idOrg);

    List<ComposicionHCOrganizacion> items = new ReporteOrganizaciones(RepoOrganizacion.getInstance())
        .composicionHCEntre(org,inicio, fin);

    Map<String,Object> model = new HashMap<>();
    model.put("nombreOrg",org.getRazonSocial());
    model.put("items",items);
    model.put("inicio",inicio);
    model.put("fin",fin);

    return new ModelAndView(model,"agenteOrgComposicionHCRespuesta.hbs");
/*
    PeriodoMensual periodo = new PeriodoMensual(LocalDate.of(2020,7,15));
    PeriodoMensual periodo2 = new PeriodoMensual(LocalDate.of(2020,8,15));
    PeriodoMensual periodo3 = new PeriodoMensual(LocalDate.of(2020,9,15));


    List<ComposicionHCOrganizacion> items = new ArrayList<>();
    Organizacion org = new Organizacion();
    ComposicionHCOrganizacion comp1 = new ComposicionHCOrganizacion(org,2900D,200D, periodo);
    ComposicionHCOrganizacion comp2 = new ComposicionHCOrganizacion(org,6000D, 3000D, periodo2);
    ComposicionHCOrganizacion comp3 = new ComposicionHCOrganizacion(org,4200D, 2400D, periodo3);
    items.add(comp1);
    items.add(comp2);
    items.add(comp3);


    Map<String,Object> model = new HashMap<>();
    model.put("nombreOrg","PIRULO S.A");
    model.put("items",items);
    model.put("inicio",inicio);
    model.put("fin",fin);
    model.put("valorAnual",9600D);
    model.put("valorMensual",300D);
    */

  }


}