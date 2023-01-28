package controllers.miembro;

import cuenta.MiembroCuenta;
import repositorios.RepoCuentas;
import miembro.Miembro;
import spark.Request;

//TODO ESTO NO ES UN CONTROLLER
public class MiembroController {

  static Miembro obtenerMiembro(Request request){
    MiembroCuenta usuario = request.session().attribute("cuenta");
    Miembro miembro = RepoCuentas.getInstance().obtenerMiembro(usuario);
    return miembro;
  }
}
