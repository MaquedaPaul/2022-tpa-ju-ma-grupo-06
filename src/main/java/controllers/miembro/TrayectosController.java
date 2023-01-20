package controllers.miembro;

import exceptions.NoConcuerdaInicioYFin;
import linea.PuntoUbicacion;
import miembro.Miembro;
import repositorios.RepoMiembros;
import repositorios.RepoTransporte;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import transporte.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrayectosController {
  public ModelAndView getTrayectos(Request request, Response response) {
    return new ModelAndView(null,"miembroTrayectos.hbs");
  }

  public ModelAndView getRegistrarTrayecto(Request request, Response response) {
    BuilderTrayecto trayecto = request.session().attribute("trayecto");
    if (trayecto == null) {
      request.session().attribute("trayecto", new BuilderTrayecto());
    }
    return new ModelAndView(trayecto, "miembroRegistrarTrayecto.hbs");
  }
  public ModelAndView getTrayectoNuevo(Request request, Response response) {

    Map<String, Object> map = mapearTransportePortipo();
    return new ModelAndView(map,"miembroTrayectoNuevo.hbs");
  }

  private Map<String, Object> mapearTransportePortipo() {
    Map<String, Object> map = new HashMap<>();
    List<Transporte> transportesPublicos = RepoTransporte.Instance.queryTransportesPor("TransportePublico");
    List<Transporte> transportesPropulsion = RepoTransporte.Instance.queryTransportesPor("PropulsionHumana");
    List<Transporte> transportesParticulares = RepoTransporte.Instance.queryTransportesPor("VehiculoParticular");
    List<Transporte> transportesServicio = RepoTransporte.Instance.queryTransportesPor("ServicioContratado");
    map.put("transportesPublicos", transportesPublicos);
    map.put("transportesPropulsion", transportesPropulsion);
    map.put("transportesParticulares", transportesParticulares);
    map.put("transportesServicio", transportesServicio);
    return map;
  }

  public ModelAndView cargarTramo(Request request, Response response) throws NoConcuerdaInicioYFin {
    Map<String, Object> model = new HashMap<>();
    String[] queryParamsArray = request.queryParams("tipo-transporte").split(" ");
    Map<String, String> mapTransporte;
    PuntoUbicacion puntoPartida;
    PuntoUbicacion puntoLlegada;
    TipoTransporte tipoTranporte;
    Transporte transporte;

    try {
      mapTransporte = MappeadorTransporte.valueOf(queryParamsArray[0])
          .mapearTransporte(queryParamsArray);
    } catch (IndexOutOfBoundsException | IllegalArgumentException exception) {
      model = mapearTransportePortipo();
      model.put("tramoIncorecto", true);
      return new ModelAndView(model,"miembroTrayectoNuevo.hbs");
    }

    try {
      puntoPartida = new PuntoUbicacion(
          Integer.parseInt(request.queryParams("localidad-partida")),
          request.queryParams("calle-partida"),
          Integer.parseInt(request.queryParams("altura-partida"))
      );

      puntoLlegada = new PuntoUbicacion(
          Integer.parseInt(request.queryParams("localidad-llegada")),
          request.queryParams("calle-llegada"),
          Integer.parseInt(request.queryParams("altura-llegada"))
      );

    } catch (NumberFormatException numberFormatException) {
      model = mapearTransportePortipo();
      model.put("tramoIncorecto", true);
      return new ModelAndView(model,"miembroTrayectoNuevo.hbs");
    }

    try {
      tipoTranporte = TipoTransporte.valueOf(queryParamsArray[0]);
    } catch (IllegalArgumentException illegalArgumentException) {
      model = mapearTransportePortipo();
      model.put("tramoIncorecto", true);
      return new ModelAndView(model,"miembroTrayectoNuevo.hbs");
    }

    transporte = tipoTranporte.getTransporte(mapTransporte);

    if (transporte == null) {
      model = mapearTransportePortipo();
      model.put("tramoIncorecto", true);
      return new ModelAndView(model,"miembroTrayectoNuevo.hbs");
    }

    BuilderTrayecto trayecto = request.session().attribute("trayecto");


    try {
      trayecto.setTransporte(transporte).setPuntoDestino(puntoLlegada);
      trayecto.setPuntoOrigen(puntoPartida);
    } catch(Exception e) {
      model = mapearTransportePortipo();
      model.put("tramoIncorecto", true);
      return new ModelAndView(model,"miembroTrayectoNuevo.hbs");
    }

    trayecto.agregarTramo();
    request.session().attribute("trayecto", trayecto);
    response.redirect("/home/trayectos/registro");
    return null;
  }

  public ModelAndView eliminarTramo(Request request, Response response) {

    BuilderTrayecto trayecto = request.session().attribute("trayecto");
    if (!trayecto.getTramos().isEmpty()) {
      trayecto.eliminarUltimoTramo();
    }
    request.session().attribute("trayecto", trayecto);
    response.redirect("/home/trayectos/registro");
    return null;
  }

  public ModelAndView cancelarTrayecto(Request request, Response response) {

    BuilderTrayecto trayecto = new BuilderTrayecto();
    request.session().attribute("trayecto", trayecto);
    response.redirect("/home/trayectos/registro");
    return null;
  }

  @Transactional
  public ModelAndView crearTrayecto(Request request, Response response) {

    BuilderTrayecto trayecto = request.session().attribute("trayecto");
    Trayecto trayectoNuevo = trayecto.build();

    Map<String, Object> model = new HashMap<>();

    if (trayectoNuevo.getTramos().isEmpty()) {

      model.put("trayectoVacio",true);
      return new ModelAndView(model,"miembroRegistrarTrayecto.hbs");
    } else {
      request.session().attribute("trayecto", null);

      Miembro miembro = MiembroController.obtenerMiembro(request);
      miembro.registrarTrayecto(trayectoNuevo);
      RepoMiembros.getInstance().agregarMiembro(miembro);

      model.put("trayectoCargadoConExito",true);
      return new ModelAndView(model,"miembroRegistrarTrayecto.hbs");

    }
  }
}
