package controllers.miembro;

import linea.LineaTransporte;
import repositorios.RepoTransporte;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import transporte.*;
import utils.GeneradorDeCategorias;

import java.util.HashMap;
import java.util.Map;

public class TrayectosController {

  public ModelAndView getTrayectos(Request request, Response response) {
    return new ModelAndView(null,"miembroTrayectos.hbs");
  }

  public ModelAndView getNuevoTrayecto(Request request, Response response) {
    boolean esLaPrimeraCarga = false;
    if (request.session().attribute("nuevo-trayecto") == null) {
      request.session().attribute("nuevo-trayecto",new BuilderTrayecto());
      esLaPrimeraCarga = true;
    }

    BuilderTrayecto bTrayecto = request.session().attribute("nuevo-trayecto");
    Map<String, Object> model = new HashMap<>();
    model.put("tramos",bTrayecto.getTramos());
    model.put("ultimoTramo",bTrayecto.getUltimoTramo());
    model.put("hayAlgunTramo",bTrayecto.sePuedeConstruir());
    model.put("noHayTramos",!bTrayecto.sePuedeConstruir() && !esLaPrimeraCarga);
    return new ModelAndView(model,"miembroNuevoTrayecto.hbs");
  }

  public ModelAndView getTransporte(Request request, Response response) {
    Map<String, Object> model = new HashMap<>();
    model.put("categorias",new GeneradorDeCategorias().generar());
    return new ModelAndView(model,"miembroTransporteTramo.hbs");
  }

  public ModelAndView getRecorrido(Request request, Response response) {

    if (request.session().attribute("nuevo-trayecto") == null) {
      response.redirect("/not-found");
      response.status(404);
      return null;
    }

    Map<String, Object> model = new HashMap<>();
    if (request.session().attribute("transporte-seleccionado") == null) {
      response.redirect("/not-found");
      response.status(404);
      return null;
    }

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
    return new ModelAndView(model,"miembroTrayectos.hbs");
  }

  public ModelAndView getParadas(Request request, Response response) {

    if (request.queryParams("sentido") == null
        || request.session().attribute("transporte-seleccionado") == null) {
      response.redirect("/not-found");
      response.status(404);
      return null;
    }

    String sentido = request.queryParams("sentido").toUpperCase();
    request.session().attribute("sentido",sentido);
    TransportePublico transporte = request.session().attribute("transporte-seleccionado");
    Map<String,Object> model = new HashMap<>();
    model.put("transporte",transporte);
    model.put("sentido",sentido);

    switch (sentido) {
      case "IDA":
        model.put("paradas",transporte.getLineaUtilizada().getRecorridoDeIda());
        break;
      case "VUELTA":
        model.put("paradas",transporte.getLineaUtilizada().getRecorridoVuelta());
        break;
      default:
        response.redirect("/not-found");
        response.status(404);
        return null;
    }

    if (request.session().attribute("origen-incorrecto") == null) {
      model.put("origen-incorrecto",true);
      request.session().attribute("origen-incorrecto",null);
    }

    if (request.session().attribute("destino-incorrecto") == null) {
      model.put("destino-incorrecto",true);
      request.session().attribute("destino-incorrecto",null);
    }

    if (request.session().attribute("destino-antes-de-origen") == null) {
      model.put("destino-antes-de-origen",true);
      request.session().attribute("destino-antes-de-origen",null);
    }

    return new ModelAndView(model,"miembroRecorridoTransportePublico.hbs");
  }

  public ModelAndView getDatosTramo(Request request, Response response) {
    return new ModelAndView(null,"miembroDatosTramo.hbs");
  }


  public Response postNuevoTramo(Request request, Response response) {
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

  public Response postPuntosUbicacion(Request request, Response response) {

    if (request.queryParams("origen") == null
        || request.queryParams("destino") == null
        || !request.queryParams("origen").matches("\\d+")
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

    request.session().attribute("kmOrigen",kmOrigen);
    request.session().attribute("kmDestino",kmDestino);
    response.redirect("/home/trayectos/nuevo-trayecto/nuevo-tramo/datos-tramo");
    return response;
  }

  public Response postBorrarNuevoTramo(Request request, Response response) {
    return response;
  }

  public Response postNuevoTrayecto(Request request, Response response) {
    return response;
  }

}
