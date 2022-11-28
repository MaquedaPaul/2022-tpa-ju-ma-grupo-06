package controllers;

import cuenta.OrganizacionCuenta;
import mediciones.Medicion;
import repositorios.*;
import lectorcsv.FormatoDeFechas;
import lectorcsv.LectorDeCsv;
import lectorcsv.TipoPerioricidad;
import lectorcsv.ValidadorDeCabeceras;
import miembro.Miembro;
import organizacion.Organizacion;
import organizacion.Sector;
import organizacion.Solicitud;
import organizacion.periodo.PeriodoMensual;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import tipoconsumo.TipoConsumo;


import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;

public class OrganizacionController extends AccountController {

  private Organizacion obtenerOrganizacion(Request request){
    OrganizacionCuenta usuario = request.session().attribute("cuenta");
    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario);
    return organizacion;
  }

  public ModelAndView getPage(Request request, Response response) {
    Organizacion organizacion = obtenerOrganizacion(request);

    Map<String, Object> model = new HashMap<>();
    //RepoSolicitud.init();
    //model.put("solicitudes",RepoSolicitud.getSolicitudesv2());

    List<Solicitud> solicitudesSinProcesar = new ArrayList<>(organizacion.getSolicitudesSinProcesar());
    model.put("solicitudes",solicitudesSinProcesar);
    //TODO SOLO MOSTRAR LAS QUE NO ESTAN PROCESADAS
    return new ModelAndView(organizacion, "organizacionGestionarVinculaciones.hbs");
  }

  public ModelAndView getCalculadoraHc(Request request, Response response) {
    Organizacion organizacion = obtenerOrganizacion(request);
    return new ModelAndView(organizacion, "organizacionCalculadoraHc.hbs");
  }

  public ModelAndView getHcTotal(Request request, Response response) {
    Organizacion organizacion = obtenerOrganizacion(request);
    double valor = organizacion.calcularHCTotal(new PeriodoMensual(LocalDate.now()));
    Map<String, Object> model = new HashMap<>();
    model.put("valorhc",valor);
    return new ModelAndView(model, "organizacionHcTotal.hbs");
  }

  public ModelAndView getImpactoMiembroBuscar(Request request, Response response) {
    Organizacion organizacion = obtenerOrganizacion(request);
    Map<String, Object> model = new HashMap<>();

    return new ModelAndView(model, "organizacionImpactoMiembro.hbs");
  }

  public ModelAndView getImpactoMiembro(Request request, Response response) {
    Organizacion organizacion = obtenerOrganizacion(request);
    Map<String, Object> model = new HashMap<>();

    String idMiembro = request.queryParams("miembro");
    response.redirect("/home/calculadora-hc/impacto-de-miembro/"+idMiembro);

    return null;
  }

  public ModelAndView getImpactoMiembroConId(Request request, Response response) {
    Organizacion organizacion = obtenerOrganizacion(request);
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
    Organizacion organizacion = obtenerOrganizacion(request);

    String nombreSector = request.queryParams("sector");
    response.redirect("/home/calculadora-hc/indicador-hc-sector/"+nombreSector);

    return null;
  }

  public ModelAndView getIndicadorHcSectorBuscar(Request request, Response response) {
    Organizacion organizacion = obtenerOrganizacion(request);
    return new ModelAndView(organizacion, "organizacionIndicadorHcSector.hbs");
  }

  public ModelAndView getIndicadorHcSectorConNombre(Request request, Response response) {
    Organizacion organizacion = obtenerOrganizacion(request);
    Map<String, Object> model = new HashMap<>();
    String nombreSector = request.params("nombre").toLowerCase();
    //.toLowerCase()
    Sector sector =organizacion.obtenerSectorSinCaseSensitive(nombreSector);
    double hcPromedio = sector.calcularPromedioHCPorMiembroPorMes();
    model.put("nombre",sector.getNombre());
    model.put("hcpromedio",hcPromedio);
    return new ModelAndView(model, "organizacionIndicadorHcSector.hbs");
  }


  public ModelAndView getMediciones(Request request, Response response) {
    Organizacion organizacion = obtenerOrganizacion(request);
    return new ModelAndView(organizacion, "organizacionRegistrarMediciones.hbs");
  }

  public ModelAndView getMedicionesArchivo(Request request, Response response) {
    Organizacion organizacion = obtenerOrganizacion(request);
    return new ModelAndView(organizacion, "organizacionCargarArchivoMedicion.hbs");
  }

  public ModelAndView getMedicionesPerse(Request request, Response response) {
    Organizacion organizacion = obtenerOrganizacion(request);
    Map<String, Object> model = new HashMap<>();
    //Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario).get(0);
    model.put("tipoconsumos",RepoTipoDeConsumo.getInstance().getTiposConsumo());


    return new ModelAndView(model, "organizacionCargarMedicion.hbs");
  }
  public ModelAndView crearMedicion(Request request, Response response) {
    Organizacion organizacion = obtenerOrganizacion(request);
    Map<String, Object> model = new HashMap<>();
    String tipoDeConsumo = request.queryParams("tipo-de-consumo");
    String periodicidad = request.queryParams("periodicidad").toUpperCase();

    double valor = Double.parseDouble(request.queryParams("valor"));
    String fecha = request.queryParams("fecha-medicion");
    DateTimeFormatter formatoFecha = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM")
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 15)
            .toFormatter();
    LocalDate fechaParseada = LocalDate.parse(fecha,formatoFecha);

    TipoPerioricidad tipoPerioricidad = TipoPerioricidad.valueOf(periodicidad);

    TipoConsumo unTipoConsumo = RepoTipoDeConsumo.getInstance().getTipoConsumo(tipoDeConsumo);
    Medicion medicion= tipoPerioricidad.buildMedicion(organizacion,unTipoConsumo,fechaParseada,valor);
    RepoMediciones.getInstance().cargarMedicion(medicion);

    response.redirect("/home");

    return null;
  }


  /* //TODO: ESTO ES UN PASAMANO
  Set<Solicitud> solicitudesSinProcesarDe(Organizacion organizacion){
    Set<Solicitud> solicitudesSinProcesar = organizacion.getSolicitudesSinProcesar();
    return solicitudesSinProcesar;
  }
  */

  private void procesarVinculacion(boolean aceptar,String usuario, Request request, Response response){
    Organizacion organizacion = obtenerOrganizacion(request);
    Map<String, Object> model = new HashMap<>();
    Long idVinculacion = Long.valueOf((request.params("id")));
    System.out.println(idVinculacion);
    //Solicitud solicitud = organizacion.getSolicitudPorId(idVinculacion);
    Solicitud solicitud = RepoSolicitud.getInstance().getSolicitudById(idVinculacion);
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


  public ModelAndView subirCSVs(Request request, Response response) throws IOException, ServletException {
    Organizacion organizacion = obtenerOrganizacion(request);

    File uploadDir = new File("upload");
    uploadDir.mkdir();
    //staticFiles.externalLocation("upload");
    Path tempFile = Files.createTempFile(uploadDir.toPath(), "", ".csv");

    request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
    try (InputStream input = request.raw().getPart("uploaded_file").getInputStream()) {
      Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
      // Use the input stream to create a file
    }



    LectorDeCsv lectorDeCsv = inicializarLectorCSV(organizacion,tempFile.toString());
    lectorDeCsv.leerMediciones();
    lectorDeCsv.cargarMediciones();
    response.redirect("/home");
    return null;
  }
  private LectorDeCsv inicializarLectorCSV(Organizacion organizacion, String path){

    FormatoDeFechas formato;
    ValidadorDeCabeceras validador;
    LectorDeCsv lector;

    DateTimeFormatter formatoMensual = new DateTimeFormatterBuilder()
            .appendPattern("MM/yyyy")
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 15)
            .toFormatter();
    DateTimeFormatter formatoAnual = new DateTimeFormatterBuilder()
            .appendPattern("yyyy")
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 15)
            .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
            .toFormatter();

    HashMap<TipoPerioricidad, DateTimeFormatter> formatos = new HashMap<>();
    formatos.put(TipoPerioricidad.ANUAL, formatoAnual);
    formatos.put(TipoPerioricidad.MENSUAL, formatoMensual);
    formato = new FormatoDeFechas(formatos);

    List<String> columnasEsperadas = new ArrayList<>();
    columnasEsperadas.add("utils/tipoconsumo");
    columnasEsperadas.add("valor");
    columnasEsperadas.add("perioricidad");
    columnasEsperadas.add("periodo de imputacion");

    validador = new ValidadorDeCabeceras(columnasEsperadas);
    try {
      lector = new LectorDeCsv(path, organizacion, formato, validador, RepoTipoDeConsumo.getInstance());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return lector;
  }


}
