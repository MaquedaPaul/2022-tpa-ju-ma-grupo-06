package controllers;

import cuenta.RepoCuentas;
import miembro.Miembro;
import miembro.RepoMiembros;
import organizacion.Organizacion;
import organizacion.Sector;
import organizacion.Solicitud;
import organizacion.TipoOrganizacion;
import organizacion.periodo.PeriodoMensual;
import organizacion.repositorio.RepoOrganizacion;
import organizacion.repositorio.RepoSolicitud;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class OrganizacionController extends AccountController {

  private String comprobarSessionParaOrganizacion(Request request, Response response){
    String usuario = comprobarSession(request, response);

    comprobarTipoCuenta(request, response, "organizacion");
    return usuario;

  }

  public ModelAndView getPage(Request request, Response response) {
    String usuario = comprobarSessionParaOrganizacion(request,response);


    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario).get(0);

    Map<String, Object> model = new HashMap<>();
    //RepoSolicitud.init();
    //model.put("solicitudes",RepoSolicitud.getSolicitudesv2());

    List<Solicitud> solicitudesSinProcesar = new ArrayList<>(organizacion.getSolicitudesSinProcesar());
    model.put("solicitudes",solicitudesSinProcesar);
    //TODO SOLO MOSTRAR LAS QUE NO ESTAN PROCESADAS
    return new ModelAndView(organizacion, "organizacionGestionarVinculaciones.hbs");
  }

  public ModelAndView getCalculadoraHc(Request request, Response response) {
    String usuario = comprobarSessionParaOrganizacion(request,response);
    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario).get(0);
    return new ModelAndView(organizacion, "organizacionCalculadoraHc.hbs");
  }

  public ModelAndView getHcTotal(Request request, Response response) {
    String usuario = comprobarSessionParaOrganizacion(request,response);

    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario).get(0);
    double valor = organizacion.calcularHCTotal(new PeriodoMensual(LocalDate.now()));
    Map<String, Object> model = new HashMap<>();
    model.put("valorhc",valor);
    return new ModelAndView(model, "organizacionHcTotal.hbs");
  }

  public ModelAndView getImpactoMiembroBuscar(Request request, Response response) {
    String usuario = comprobarSessionParaOrganizacion(request,response);
    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario).get(0);
    Map<String, Object> model = new HashMap<>();

    return new ModelAndView(model, "organizacionImpactoMiembro.hbs");
  }

  public ModelAndView getImpactoMiembro(Request request, Response response) {
    String usuario = comprobarSessionParaOrganizacion(request,response);
    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario).get(0);
    Map<String, Object> model = new HashMap<>();

    String idMiembro = request.queryParams("miembro");
    response.redirect("/home/calculadora-hc/impacto-de-miembro/"+idMiembro);

    return null;
  }

  public ModelAndView getImpactoMiembroConId(Request request, Response response) {
    String usuario = comprobarSessionParaOrganizacion(request,response);
    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario).get(0);
    Map<String, Object> model = new HashMap<>();
    Long idMiembro = Long.valueOf((request.params("id")));
    Miembro miembro =RepoMiembros.getInstance().getMiembrosPor(idMiembro);
    model.put("nombre",miembro.getNombre());
    model.put("apellido",miembro.getApellido());
    double impacto = organizacion.impactoDeMiembro(miembro,new PeriodoMensual(LocalDate.now()));
    model.put("impacto",impacto);
    return new ModelAndView(model, "organizacionImpactoMiembro.hbs");
  }

  public ModelAndView getIndicadorHcSector(Request request, Response response) {
    String usuario = comprobarSessionParaOrganizacion(request,response);
    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario).get(0);

    String nombreSector = request.queryParams("sector");
    response.redirect("/home/calculadora-hc/indicador-hc-sector/"+nombreSector);

    return null;
  }

  public ModelAndView getIndicadorHcSectorBuscar(Request request, Response response) {
    String usuario = comprobarSessionParaOrganizacion(request,response);

    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario).get(0);
    return new ModelAndView(organizacion, "organizacionIndicadorHcSector.hbs");
  }

  public ModelAndView getIndicadorHcSectorConNombre(Request request, Response response) {
    String usuario = comprobarSessionParaOrganizacion(request,response);
    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario).get(0);
    Map<String, Object> model = new HashMap<>();
    String nombreSector = request.params("nombre").toLowerCase();
    //.toLowerCase()
    Sector sector =organizacion.obtenerSectorPorCaseSensitive(nombreSector);
    double hcPromedio = sector.calcularPromedioHCPorMiembroPorMes();
    model.put("nombre",sector.getNombre());
    model.put("hcpromedio",hcPromedio);
    return new ModelAndView(model, "organizacionIndicadorHcSector.hbs");
  }


  public ModelAndView getMediciones(Request request, Response response) {
    String usuario = comprobarSessionParaOrganizacion(request,response);

    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario).get(0);
    return new ModelAndView(organizacion, "organizacionRegistrarMediciones.hbs");
  }

  public ModelAndView getMedicionesArchivo(Request request, Response response) {
    String usuario = comprobarSessionParaOrganizacion(request,response);

    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario).get(0);
    return new ModelAndView(organizacion, "organizacionCargarArchivoMedicion.hbs");
  }

  public ModelAndView getMedicionesPerse(Request request, Response response) {
    String usuario = comprobarSessionParaOrganizacion(request,response);


    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario).get(0);
    return new ModelAndView(organizacion, "organizacionCargarMedicion.hbs");
  }

  Set<Solicitud> solicitudesSinProcesarDe(Organizacion organizacion){
    Set<Solicitud> solicitudesSinProcesar = organizacion.getSolicitudesSinProcesar();
    return solicitudesSinProcesar;
  }

  private void procesarVinculacion(boolean aceptar,String usuario, Request request, Response response){
    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario).get(0);
    Map<String, Object> model = new HashMap<>();
    Long idVinculacion = Long.valueOf((request.params("id")));
    System.out.println(idVinculacion);
    Solicitud solicitud =organizacion.getSolicitudPorId(idVinculacion);
    //Solicitud solicitud = RepoSolicitud.getInstance().getSolicitudPor(idVinculacion);
    organizacion.procesarVinculacion(solicitud,aceptar);
    RepoOrganizacion.getInstance().agregarOrganizacion(organizacion);

  }


  public ModelAndView aceptarVinculacion(Request request, Response response) {
    String usuario = comprobarSession(request, response);
    comprobarTipoCuenta(request, response, "organizacion");
    procesarVinculacion(true,usuario,request,response);
    response.redirect("/home/vinculaciones");

    return null;
  }


  public ModelAndView rechazarVinculacion(Request request, Response response) {
    String usuario = comprobarSession(request, response);
    comprobarTipoCuenta(request, response, "organizacion");
    procesarVinculacion(false,usuario,request,response);
    response.redirect("/home/vinculaciones");
    return null;

  }



}
