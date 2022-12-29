package controllers;

import cuenta.MiembroCuenta;
import cuenta.OrganizacionCuenta;
import linea.BuilderPuntoUbicacion;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import repositorios.RepoCuentas;
import exceptions.NoConcuerdaInicioYFin;
import linea.PuntoUbicacion;
import miembro.Miembro;
import organizacion.Organizacion;
import organizacion.Sector;
import organizacion.Solicitud;
import repositorios.RepoMiembros;
import repositorios.RepoOrganizacion;
import repositorios.RepoTransporte;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import transporte.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiembroController {

  private Miembro obtenerMiembro(Request request){
    MiembroCuenta usuario = request.session().attribute("cuenta");
    Miembro miembro = RepoCuentas.getInstance().obtenerMiembro(usuario);
    return miembro;
  }

  private String obtenerUsuario (Request request){
    MiembroCuenta cuenta = request.session().attribute("cuenta");
    return cuenta.getUsuario();
  }


  public ModelAndView getTrayectos(Request request, Response response) {

    /*comprobarSession(request, response);
    comprobarTipoCuenta(request, response, "miembro");*/
    return new ModelAndView(null,"miembroTrayectos.hbs");
  }

  public ModelAndView pedirVinculacion(Request request, Response response) {
/*    String usuario = comprobarSession(request, response);
    comprobarTipoCuenta(request, response, "miembro");*/

    String organizacionSolicitada = request.queryParams("organizacionSolicitada");
    String sectorSolicitado = request.queryParams("sectorSolicitado");
    Organizacion organizacionObjetivo = RepoOrganizacion.getInstance().getOrganizacionPor(organizacionSolicitada);

    if (organizacionObjetivo == null) {
      response.redirect("/home/vinculacion");
      return null;
    }
    Sector sectorObjetivo = organizacionObjetivo.obtenerSectorPor(sectorSolicitado);
    if (sectorObjetivo == null) {
      response.redirect("/home/vinculacion");
      return null;
    }
    Miembro miembro =  obtenerMiembro(request);
    miembro.solicitarVinculacion(organizacionObjetivo, new Solicitud(miembro, sectorObjetivo));
    RepoOrganizacion.getInstance().agregarOrganizacion(organizacionObjetivo);
    response.redirect("/home");
    return null;
  }

  public ModelAndView getRegistro(Request request, Response response) {
    /*comprobarSession(request, response);
    comprobarTipoCuenta(request, response, "miembro");*/
    return new ModelAndView(null,"registro.hbs");
  }

  public ModelAndView getRegistrarTrayecto(Request request, Response response) {
    /*comprobarSession(request, response);
    comprobarTipoCuenta(request, response, "miembro");*/
    BuilderTrayecto trayecto = request.session().attribute("trayecto");
    if (trayecto == null) {
      request.session().attribute("trayecto", new BuilderTrayecto());
    }
    return new ModelAndView(trayecto, "miembroRegistrarTrayecto.hbs");
  }

  public ModelAndView getVinculacion(Request request, Response response) {
    /*comprobarSession(request, response);
    comprobarTipoCuenta(request, response, "miembro");*/
    List<Organizacion> organizaciones =  RepoOrganizacion.getInstance().getOrganizaciones();
    List<String> nombreSectores = new ArrayList<>(RepoOrganizacion.getInstance().nombreDeTodosLosSectores());
    HashMap<String, Object> hashMap = new HashMap<>();
    hashMap.put("nombreSectores",nombreSectores);
    hashMap.put("organizaciones", organizaciones);
    return new ModelAndView(hashMap,"miembroVinculacion.hbs");
  }

  public ModelAndView getTrayectoNuevo(Request request, Response response) {
    /*comprobarSession(request, response);
    comprobarTipoCuenta(request, response, "miembro");*/
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
      puntoPartida = new BuilderPuntoUbicacion()
          .setLocalidadId(request.queryParams("localidad-partida"))
          .setAltura(request.queryParams("altura-partida"))
          .setCalle(request.queryParams("calle-partida"))
          .build();
      puntoLlegada = new BuilderPuntoUbicacion()
          .setLocalidadId(request.queryParams("localidad-llegada"))
          .setAltura(request.queryParams("altura-llegada"))
          .setCalle(request.queryParams("calle-llegada"))
          .build();
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
    /*comprobarSession(request, response);
    comprobarTipoCuenta(request, response, "miembro");*/
    BuilderTrayecto trayecto = request.session().attribute("trayecto");
    if (!trayecto.getTramos().isEmpty()) {
      trayecto.eliminarUltimoTramo();
    }
    request.session().attribute("trayecto", trayecto);
    response.redirect("/home/trayectos/registro");
    return null;
  }

  public ModelAndView cancelarTrayecto(Request request, Response response) {
    /*comprobarSession(request, response);
    comprobarTipoCuenta(request, response, "miembro");*/
    BuilderTrayecto trayecto = new BuilderTrayecto();
    request.session().attribute("trayecto", trayecto);
    response.redirect("/home/trayectos/registro");
    return null;
  }

  @Transactional
  public ModelAndView crearTrayecto(Request request, Response response) {
    /*comprobarSession(request, response);
    comprobarTipoCuenta(request, response, "miembro");*/
    BuilderTrayecto trayecto = request.session().attribute("trayecto");
    Trayecto trayectoNuevo = trayecto.build();

    Map<String, Object> model = new HashMap<>();

    if (trayectoNuevo.getTramos().isEmpty()) {
      //response.redirect("/home/trayectos/registro");
      model.put("trayectoVacio",true);
      return new ModelAndView(model,"miembroRegistrarTrayecto.hbs");
    } else {
      request.session().attribute("trayecto", null);

      Miembro miembro = obtenerMiembro(request);
      miembro.registrarTrayecto(trayectoNuevo);
      RepoMiembros.getInstance().agregarMiembro(miembro);

      model.put("trayectoCargadoConExito",true);
      return new ModelAndView(model,"miembroRegistrarTrayecto.hbs");
      //response.redirect("/home/trayectos/registro");
    }
  }
}
