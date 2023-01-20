package controllers.miembro;

import cuenta.MiembroCuenta;
import cuenta.OrganizacionCuenta;
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

  static Miembro obtenerMiembro(Request request){
    MiembroCuenta usuario = request.session().attribute("cuenta");
    Miembro miembro = RepoCuentas.getInstance().obtenerMiembro(usuario);
    return miembro;
  }

  static String obtenerUsuario (Request request){
    MiembroCuenta cuenta = request.session().attribute("cuenta");
    return cuenta.getUsuario();
  }



  public ModelAndView getRegistro(Request request, Response response) {
    return new ModelAndView(null,"registro.hbs");
  }

}
