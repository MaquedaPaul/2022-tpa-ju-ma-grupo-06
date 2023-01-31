package controllers.miembro;

import linea.LineaTransporte;
import linea.PuntoUbicacion;
import miembro.Miembro;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import repositorios.RepoMiembros;
import repositorios.RepoTransporte;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import transporte.*;
import utils.GeneradorDeCategorias;


import java.util.HashMap;
import java.util.Map;

public class TrayectosController implements WithGlobalEntityManager {

  public ModelAndView getTrayectos(Request request, Response response) {
    Map<String, Object> model = new HashMap<>();
    model.put("seCreoUnTrayecto",request.session().attribute("trayecto-creado"));
    return new ModelAndView(model,"miembroTrayectos.hbs");
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
      model.put("origenNoConcuerda",request.session().attribute("punto-origen-no-concuerda"));
      request.session().attribute("punto-origen-no-concuerda",false);
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

  public Response postPuntosUbicacion(Request request, Response response) {

    if ((request.queryParams("localidad_id_origen") == null
            || request.queryParams("calle_origen") == null
            || request.queryParams("altura_origen") == null
            || request.queryParams("localidad_id_destino") == null
            || request.queryParams("calle_destino") == null
            || request.queryParams("altura_destino") == null) &&
    (request.queryParams("origen") == null
        || request.queryParams("destino") == null)) {
      response.redirect("/home/trayectos");
      return response;
    }

    if (request.queryParams("localidad_id_origen") == null) {
      return this.postParadas(request,response);
    }
    return this.postPuntos(request,response);
  }

  private Response postParadas(Request request, Response response) {
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

  private Response postPuntos(Request request, Response response) {

    String localidad_id_origen = request.queryParams("localidad_id_origen");
    String localidad_id_destino = request.queryParams("localidad_id_destino");
    String altura_origen = request.queryParams("altura_origen");
    String altura_destino = request.queryParams("altura_destino");
    String calle_origen = request.queryParams("calle_origen");
    String calle_destino = request.queryParams("calle_destino");

    if (!localidad_id_origen.matches("\\d+")
            || !localidad_id_destino.matches("\\d+")
            || !altura_destino.matches("\\d+")
            || !altura_origen.matches("\\d+")) {
      response.redirect("/home/trayectos/nuevo-trayecto/nuevo-tramo/recorrido");
      return response;
    }

    PuntoUbicacion origen = new PuntoUbicacion(Integer.parseInt(localidad_id_origen), calle_origen,Integer.parseInt(altura_origen));
    PuntoUbicacion destino = new PuntoUbicacion(Integer.parseInt(localidad_id_destino), calle_destino,Integer.parseInt(altura_destino));

    if (!this.concuerdaOrigenConElUltimoTram(origen, request)) {
      request.session().attribute("punto-origen-no-concuerda",true);
      response.redirect("/home/trayectos/nuevo-trayecto/nuevo-tramo/recorrido");
      return response;
    }
    request.session().attribute("punto-origen", origen);
    request.session().attribute("punto-destino", destino);
    response.redirect("/home/trayectos/nuevo-trayecto/nuevo-tramo/datos-tramo");
    return response;
  }

  private boolean concuerdaOrigenConElUltimoTram(PuntoUbicacion punto, Request request) {
    BuilderTrayecto bTrayecto = request.session().attribute("nuevo-trayecto");
    Tramo ultimo = bTrayecto.getUltimoTramo();
    return ultimo == null || ultimo.getPuntoDestino().equals(punto);
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
    Trayecto t = bTrayecto.build();
    entityManager().getTransaction().begin();
    entityManager().persist(t);
    entityManager().getTransaction().commit();
    System.out.println(miembro.getTrayectos().size());
    miembro = RepoMiembros.getInstance().getMiembrosPor(miembro.getId());
    miembro.registrarTrayecto(t);
    entityManager().getTransaction().begin();
    entityManager().persist(miembro);
    entityManager().getTransaction().commit();
    this.limpiarDatosDelTrayectoDeLaSession(request);
    System.out.println(miembro.getTrayectos().size());
    request.session().attribute("trayecto-creado",true);
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
