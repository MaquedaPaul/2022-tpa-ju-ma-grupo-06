package controllers;

import cuenta.RepoCuentas;
import miembro.Miembro;
import organizacion.Organizacion;
import organizacion.Sector;
import organizacion.Solicitud;
import organizacion.repositorio.RepoOrganizacion;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MiembroController {
  public ModelAndView getTrayectos(Request request, Response response) {
    comprobarSession(request, response);
    comprobarTipoCuenta(request, response);
    return new ModelAndView(null,"trayectos.hbs");
  }

  public ModelAndView pedirVinculacion(Request request, Response response) {
    String usuario = comprobarSession(request, response);
    comprobarTipoCuenta(request, response);
    String organizacionSolicitada = request.queryParams("organizacionSolicitada");
    String sectorSolicitado = request.queryParams("sectorSolicitado");
    Organizacion organizacionObjetivo = RepoOrganizacion.getInstance()
        .getOrganizaciones().stream()
        .filter(organizacion -> organizacion.getRazonSocial().equals(organizacionSolicitada))
        .findAny().orElse(null);
    if (organizacionObjetivo == null) {
      response.redirect("/home/vinculacion");
    }
    Sector sectorObjetivo = organizacionObjetivo.obtenerSectorPor(sectorSolicitado);
    if (sectorObjetivo == null) {
      response.redirect("/home/vinculacion");
    }
    Miembro miembro =  RepoCuentas.getInstance().obtenerMiembro(usuario).get(0);
    miembro.solicitarVinculacion(organizacionObjetivo, new Solicitud(miembro, sectorObjetivo));
    response.redirect("/home");
    return null;
  }

  public ModelAndView getRegistro(Request request, Response response) {
    comprobarSession(request, response);
    comprobarTipoCuenta(request, response);
    return new ModelAndView(null,"registro.hbs");
  }

  public ModelAndView getRegistrarTrayecto(Request request, Response response) {
    comprobarSession(request, response);
    comprobarTipoCuenta(request, response);
    return new ModelAndView(null,"registrarTrayecto.hbs");
  }

  public ModelAndView getVinculacion(Request request, Response response) {
    comprobarSession(request, response);
    comprobarTipoCuenta(request, response);
    List<Organizacion> organizaciones =  RepoOrganizacion.getInstance().getOrganizaciones();
    List<Sector> sectores = new ArrayList<>(RepoOrganizacion.getInstance().obtenerTodosLosSectores());
    HashMap<String, Object> hashMap = new HashMap<>();
    hashMap.put("sectores",sectores);
    hashMap.put("organizaciones", organizaciones);
    return new ModelAndView(hashMap,"vinculacion.hbs");
  }

  public String comprobarSession(Request request, Response response) {
    String usuario = request.session().attribute("logged_user");
    String tipoCuenta = request.session().attribute("logged_user");
    if (usuario == null ) {
      response.redirect("/signin");
    }
    return usuario;
  }

  public void comprobarTipoCuenta(Request request,  Response response) {
    String tipoUsuario = request.session().attribute("user-type");
    if (!tipoUsuario.equals("miembro")) {
      response.redirect("/home");
    }
  }
}
