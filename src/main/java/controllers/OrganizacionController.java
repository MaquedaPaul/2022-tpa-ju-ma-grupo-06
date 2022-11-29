package controllers;

import cuenta.OrganizacionCuenta;
import lectorcsv.*;
import mediciones.Medicion;
import organizacion.periodo.PeriodoAnual;
import repositorios.*;
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
  private String obtenerUsuario (Request request){
    OrganizacionCuenta cuenta = request.session().attribute("cuenta");
    return cuenta.getUsuario();
  }

  public ModelAndView getVinculaciones(Request request, Response response) {
    Organizacion organizacion = obtenerOrganizacion(request);
    Map<String, Object> model = new HashMap<>();
    model.put("solicitudes",organizacion.getSolicitudesSinProcesar());
    return new ModelAndView(model, "organizacionGestionarVinculaciones.hbs");
  }

  public ModelAndView getCalculadoraHc(Request request, Response response) {
    Organizacion organizacion = obtenerOrganizacion(request);
    return new ModelAndView(organizacion, "organizacionCalculadoraHc.hbs");
  }

  public ModelAndView getHcTotal(Request request, Response response) {
    Organizacion organizacion = obtenerOrganizacion(request);
    int anioActual = LocalDate.now().getYear();
    //TODO
    double valor = organizacion.calcularHCTotal(new PeriodoAnual(LocalDate.of(anioActual,1, 15)));
    Map<String, Object> model = new HashMap<>();
    model.put("valorhc",valor);
    return new ModelAndView(model, "organizacionHcTotal.hbs");
  }

  public ModelAndView getImpactoMiembroBuscar(Request request, Response response) {
    Map<String, Object> model = new HashMap<>();
    model.put("miembroNoNull", true);
    model.put("comprobacion", true);

    return new ModelAndView(model, "organizacionImpactoMiembro.hbs");
  }

  public ModelAndView getImpactoMiembro(Request request, Response response) {
    Map<String, Object> model = new HashMap<>();

    String nombreApellido = request.queryParams("miembro");
    response.redirect("/home/calculadora-hc/impacto-de-miembro/"+nombreApellido);

    return null;
  }

  public ModelAndView getImpactoMiembroConNombreYApellido(Request request, Response response) {
    Organizacion organizacion = obtenerOrganizacion(request);
    Map<String, Object> model = new HashMap<>();
    String nombreApellido = request.params("nombreApellido");
    Miembro miembro =RepoMiembros.getInstance().getMiembrosPorNombreYApellido(nombreApellido);
    boolean miembroNoNull = miembro != null;
    model.put("miembroNoNull", miembroNoNull);
    if(miembroNoNull){
      boolean miembroPerteneceAOrg = organizacion.miembroPerteneceAlaOrganizacion(miembro);
      model.put("nombre",miembro.getNombre());
      model.put("apellido",miembro.getApellido());
      if(!miembroPerteneceAOrg){
        return new ModelAndView(model, "organizacionImpactoMiembro.hbs");
      }
      double impacto = organizacion.impactoDeMiembro(miembro,new PeriodoMensual(LocalDate.now()));
      model.put("impacto",impacto);
      model.put("comprobacion", miembroPerteneceAOrg);
      return new ModelAndView(model, "organizacionImpactoMiembro.hbs");
    }
    return new ModelAndView(model, "organizacionImpactoMiembro.hbs");
  }

  public ModelAndView getIndicadorHcSector(Request request, Response response) {
    String nombreSector = request.queryParams("sector");

    response.redirect("/home/calculadora-hc/indicador-hc-sector/"+nombreSector);
    return null;
  }

  public ModelAndView getIndicadorHcSectorBuscar(Request request, Response response) {
    Organizacion organizacion = obtenerOrganizacion(request);
    Map<String, Object> model = new HashMap<>();
    model.put("sectorNoNull", true);
    model.put("comprobacion", true);
    return new ModelAndView(model, "organizacionIndicadorHcSector.hbs");
  }

  public ModelAndView getIndicadorHcSectorConNombre(Request request, Response response) {
    Organizacion organizacion = obtenerOrganizacion(request);
    Map<String, Object> model = new HashMap<>();


    String nombreSector = request.params("nombre").toLowerCase();
    Sector sector = organizacion.obtenerSectorSinCaseSensitive(nombreSector);
    boolean sectorNoNull = sector != null;
    model.put("sectorNoNull", sectorNoNull);
    if(sectorNoNull){
      model.put("nombre",sector.getNombre());
      double hcPromedio = sector.calcularPromedioHCPorMiembroPorMes();
      model.put("hcpromedio",hcPromedio);
      return new ModelAndView(model, "organizacionIndicadorHcSector.hbs");
    }

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



  private void procesarVinculacion(boolean aceptar,String usuario, Request request, Response response){
    Organizacion organizacion = obtenerOrganizacion(request);
    Long idVinculacion = Long.valueOf((request.params("id")));
    System.out.println(idVinculacion);
    //Solicitud solicitud = organizacion.getSolicitudPorId(idVinculacion);
    Solicitud solicitud = RepoSolicitud.getInstance().getSolicitudById(idVinculacion);
    organizacion.procesarVinculacion(solicitud,aceptar);
    RepoOrganizacion.getInstance().agregarOrganizacion(organizacion);

  }


  public ModelAndView aceptarVinculacion(Request request, Response response) {
    String usuario = obtenerUsuario(request);
    procesarVinculacion(true,usuario,request,response);
    response.redirect("/home/vinculaciones");

    return null;
  }


  public ModelAndView rechazarVinculacion(Request request, Response response) {
    String usuario = obtenerUsuario(request);
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

    LectorMediciones lector = new LectorMediciones(tempFile.toString(),organizacion);
    lector.leerMediciones();
    lector.cargarMediciones();
    response.redirect("/home");
    return null;
  }



}
