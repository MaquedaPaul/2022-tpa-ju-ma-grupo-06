package controllers.miembro;

import linea.LineaTransporte;
import linea.PuntoUbicacion;
import miembro.Miembro;
import repositorios.RepoTransporte;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import transporte.*;
import utils.GeneradorDeCategorias;

import javax.activation.MimeType;
import java.util.HashMap;
import java.util.Map;

public class TrayectosController {

  public ModelAndView getTrayectos(Request request, Response response) {
    return new ModelAndView(null,"miembroTrayectos.hbs");
  }

  public ModelAndView getNuevoTrayecto(Request request, Response response) {
    if (request.session().attribute("nuevo-trayecto") == null) {
      request.session().attribute("nuevo-trayecto",new BuilderTrayecto());
    }

    boolean seIntentoCrear = request.session().attribute("se-intento-crear");

    BuilderTrayecto bTrayecto = request.session().attribute("nuevo-trayecto");
    Map<String, Object> model = new HashMap<>();
    model.put("tramos",bTrayecto.getTramos());
    model.put("ultimoTramo",bTrayecto.getUltimoTramo());
    model.put("hayAlgunTramo",bTrayecto.sePuedeConstruir());
    model.put("noHayTramos",!bTrayecto.sePuedeConstruir() && seIntentoCrear);
    model.put("puntoOrigenNoConcuerda",request.session().attribute("punto-origen-no-concuerda"));
    request.session().attribute("punto-origen-no-concuerda",false);
    request.session().attribute("se-intento-crear",false);
    return new ModelAndView(model,"miembroNuevoTrayecto.hbs");
  }

  public ModelAndView getTransporte(Request request, Response response) {
    Map<String, Object> model = new HashMap<>();
    model.put("categorias",new GeneradorDeCategorias().generar());
    return new ModelAndView(model,"miembroTransporteTramo.hbs");
  }

  public ModelAndView getRecorrido(Request request, Response response) {

    if (request.session().attribute("nuevo-trayecto") == null
        || request.session().attribute("transporte-seleccionado") == null) {
      response.redirect("/not-found");
      return null;
    }

    Map<String, Object> model = new HashMap<>();

    Transporte transporte = request.session().attribute("transporte-seleccionado");
    model.put("esPublico", transporte.getNombre().startsWith("COLECTIVO"));

    if ((boolean) model.get("esPublico")) {
      TransportePublico transportePublico = (TransportePublico) transporte;
      model.put("primerParadaIda",transportePublico.getUbicacionInicioPrimerRecorrido());
      model.put("ultimaParadaIda",transportePublico.getUltimaUbicacionPrimerRecorrido());
      model.put("primerParadaVuelta",transportePublico.getPrimeraUbicacionRecorridoVuelta());
      model.put("ultimaParadaVuelta",transportePublico.getUltimaUbicacionRecorridoVuelta());
    } else {
      BuilderTrayecto bTrayecto = request.session().attribute("nuevo-trayecto");
      model.put("hayTramos",bTrayecto.sePuedeConstruir());
      model.put("ultimoTramo",bTrayecto.getUltimoTramo());
    }
    return new ModelAndView(model,"miembroRecorrido.hbs");
  }

  public ModelAndView getParadas(Request request, Response response) {

    if (request.queryParams("sentido") == null
        || request.session().attribute("transporte-seleccionado") == null) {
      response.redirect("/not-found");
      return null;
    }

    String sentido = request.queryParams("sentido").toUpperCase();
    TransportePublico transporte = request.session().attribute("transporte-seleccionado");

    Map<String,Object> model = new HashMap<>();
    BuilderTrayecto bTrayecto = request.session().attribute("nuevo-trayecto");
    model.put("transporte",transporte);
    model.put("sentido",sentido);
    if (bTrayecto.sePuedeConstruir()) {
      model.put("ultimoPunto",bTrayecto.getUltimoTramo().getPuntoDestino());
      model.put("kmUltimoPunto",transporte.getKmEnPunto((PuntoUbicacion) model.get("ultimoPunto"),sentido));
    }

    if (bTrayecto.sePuedeConstruir()
        && !transporte.tieneUnaParadaElPunto((PuntoUbicacion) model.get("ultimoPunto"), sentido)) {
      request.session().attribute("punto-origen-no-concuerda",true);
      response.redirect("/home/trayectos/nuevo-trayecto/tramos");
      return null;
    }
    switch (sentido) {
      case "IDA":
        model.put("paradas",transporte.getLineaUtilizada().getRecorridoDeIda());
        break;
      case "VUELTA":
        model.put("paradas",transporte.getLineaUtilizada().getRecorridoVuelta());
        break;
      default:
        response.redirect("/not-found");
        return null;
    }
    request.session().attribute("sentido",sentido);
    if (request.session().attribute("origen-incorrecto")) {
      model.put("origen-incorrecto",true);
      request.session().attribute("origen-incorrecto",false);
    }

    if (request.session().attribute("destino-incorrecto")) {
      model.put("destino-incorrecto",true);
      request.session().attribute("destino-incorrecto",false);
    }

    if (request.session().attribute("destino-antes-de-origen")) {
      model.put("destino-antes-de-origen",true);
      request.session().attribute("destino-antes-de-origen",false);
    }

    return new ModelAndView(model,"miembroRecorridoTransportePublico.hbs");
  }

  public ModelAndView getDatosTramo(Request request, Response response) {

    if (request.session().attribute("punto-origen") == null
      || request.session().attribute("punto-destino") == null
      || request.session().attribute("transporte-seleccionado") == null) {
      response.redirect("/not-found");
      return null;
    }

    Transporte transporte = request.session().attribute("transporte-seleccionado");
    PuntoUbicacion origen = request.session().attribute("punto-origen");
    PuntoUbicacion destino = request.session().attribute("punto-destino");
    Map<String,Object> model = new HashMap<>();
    model.put("transporte",transporte);
    model.put("origen",origen);
    model.put("destino",destino);

    return new ModelAndView(model,"miembroDatosTramo.hbs");
  }

  public Response postNuevoTramo(Request request, Response response) {
    //EXISTE TRANSPORTE Y PUNTOS
    if ( request.session().attribute("transporte-seleccionado") == null
            || request.session().attribute("punto-origen") == null
            || request.session().attribute("punto-origen") == null) {
      response.redirect("/not-found");
      return response;
    }

    BuilderTrayecto bTrayecto = request.session().attribute("nuevo-trayecto");
    //OBTENER DATOS DE LA SESSION
    Transporte transporte = request.session().attribute("transporte-seleccionado");
    PuntoUbicacion origen = request.session().attribute("punto-origen");
    PuntoUbicacion destino = request.session().attribute("punto-destino");
    bTrayecto.agregarTramo(new Tramo(origen,destino,transporte));
    //LIMPIAR DATOS DE LA SESSION
    this.limpiarDatosDelTramoDeLaSesion(request);
    response.redirect("/home/trayectos/nuevo-trayecto/tramos");
    return response;
  }

  public Response postTransporte(Request request, Response response) {
    String nombreTransporte = request.queryParams("transportes");
    if (!RepoTransporte.Instance.existeTransporte(nombreTransporte)) {
      response.redirect("/home/trayectos/nuevo-trayecto/nuevo-tramo/transporte");
      return response;
    }
    Transporte transporte = RepoTransporte.Instance.getTransporteByName(nombreTransporte);
    request.session().attribute("transporte-seleccionado",transporte);
    response.redirect("/home/trayectos/nuevo-trayecto/nuevo-tramo/recorrido");
    return response;
  }

  //TODO ACEPTAR PUNTOS UBICACION Y PARADAS
  public Response postPuntosUbicacion(Request request, Response response) {

    int localidadIdOrigen = Integer.parseInt(request.queryParams("origen_localidad_id"));
    int localidadIdDestino = Integer.parseInt(request.queryParams("destino_localidad_id"));

    if (localidadIdOrigen <= 1 || localidadIdOrigen > 6326 && localidadIdDestino <= 1 || localidadIdDestino > 6326 ) {
      response.redirect("/home/trayectos");
      return response;
    }

    String calleOrigen = request.queryParams("origen_calle");
    int alturaOrigen = Integer.parseInt(request.queryParams("origen_altura"));
    String calleDestino = request.queryParams("destino_calle");
    int alturaDestino = Integer.parseInt(request.queryParams("destino_altura"));

    if (calleOrigen == null || calleDestino == null || !calleOrigen.matches("[a-zA-Z]+") || !calleDestino.matches("[a-zA-Z]+") || alturaOrigen < 1 || alturaDestino < 1) {
      response.redirect("/home/trayectos/nuevo-trayecto/nuevo-tramo/puntos-ubicacion");
      return response;
    }

    if (request.queryParams("origen") == null
        || request.queryParams("destino") == null) {
      response.redirect("/home/trayectos");
      return response;
    }
    if ( !request.queryParams("origen").matches("\\d+")
        || !request.queryParams("destino").matches("\\d+")) {
      response.redirect("/home/trayectos/nuevo-trayecto/nuevo-tramo/transporte/paradas");
      return response;
    }
    int kmOrigen = Integer.parseInt(request.queryParams("origen"));
    int kmDestino= Integer.parseInt(request.queryParams("destino"));
    String sentido = request.session().attribute("sentido");

    TransportePublico transporte = request.session().attribute("transporte-seleccionado");
    LineaTransporte linea = transporte.getLineaUtilizada();

    int errores = 0;
    if (!linea.existeParadaEnElKm(kmOrigen,sentido)) {
      request.session().attribute("origen-incorrecto",true);
      errores++;
    }

    if (!linea.existeParadaEnElKm(kmDestino,sentido)) {
      request.session().attribute("destino-incorrecto",true);
      errores++;
    }

    if (kmOrigen >= kmDestino) {
      request.session().attribute("destino-antes-de-origen",true);
      errores++;
    }

    if (errores != 0) {
      response.redirect("/home/trayectos/nuevo-trayecto/nuevo-tramo/transporte/paradas");
      return response;
    }

    request.session().attribute("punto-origen",transporte.getPuntoEnKm(kmOrigen, sentido));
    request.session().attribute("punto-destino",transporte.getPuntoEnKm(kmDestino, sentido));
    response.redirect("/home/trayectos/nuevo-trayecto/nuevo-tramo/datos-tramo");
    return response;
  }

  public Response postBorrarUltimoTramo(Request request, Response response) {
    //EXISTE NUEVO TRAYECTO
    if (request.session().attribute("nuevo-trayecto") == null) {
      response.redirect("/not-found");
      return response;
    }
    BuilderTrayecto bTrayecto = request.session().attribute("nuevo-trayecto");
    bTrayecto.eliminarUltimoTramo();
    response.redirect("/home/trayectos/nuevo-trayecto/tramos");
    return response;
  }

  public Response postNuevoTrayecto(Request request, Response response) {
    if (request.session().attribute("nuevo-trayecto") == null) {
      response.redirect("/not-found");
      return response;
    }
    BuilderTrayecto bTrayecto = request.session().attribute("nuevo-trayecto");

    if (!bTrayecto.sePuedeConstruir()) {
      request.session().attribute("se-intento-crear",true);
      response.redirect("/home/trayectos/nuevo-trayecto/tramos");
      return response;
    }
    Miembro miembro = request.session().attribute("miembro");
    miembro.registrarTrayecto(bTrayecto.build());
    this.limpiarDatosDelTrayectoDeLaSession(request);
    response.redirect("/home/trayectos");
    return response;
  }

  //TODO LIMPIAR DATOS TRAYECTO
  private void limpiarDatosDelTrayectoDeLaSession(Request request) {
    this.limpiarDatosDelTramoDeLaSesion(request);
    request.session().removeAttribute("nuevo-trayecto");
  }

  public Response postBorrarTodo(Request request, Response response) {

    this.limpiarDatosDelTrayectoDeLaSession(request);
    response.redirect("/home/trayectos");
    return response;
  }

  public Response postEliminarDatosBorradorTramo(Request request, Response response) {
    this.limpiarDatosDelTramoDeLaSesion(request);
    response.redirect("/home/trayectos/nuevo-trayecto/tramos");
    return response;
  }

  private void limpiarDatosDelTramoDeLaSesion(Request request) {
    request.session().removeAttribute("transporte-seleccionado");
    request.session().removeAttribute("punto-origen");
    request.session().removeAttribute("punto-origen");
  }

}
