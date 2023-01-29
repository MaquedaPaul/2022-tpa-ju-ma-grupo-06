package cuenta;

import repositorios.RepoCuentas;
import spark.Request;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class MiembroCuenta extends Cuenta {

  protected MiembroCuenta() {
  }

  public MiembroCuenta(String usuario, String password) {
    super(usuario, password);
  }

  public String home() {
    return "miembroRecomendaciones.hbs";
  }

  public TipoCuenta nivelDeAcceso() {
    return TipoCuenta.MIEMBRO;
  }

  @Override
  public void guardarEnSesion(Request request) {
    request.session().attribute("cuenta",this);
    request.session().attribute("miembro", RepoCuentas.getInstance().obtenerMiembro(this));
    request.session().attribute("origen-incorrecto",false);
    request.session().attribute("destino-incorrecto",false);
    request.session().attribute("destino-antes-de-origen",false);
  }

  @Override
  public boolean puedeAccederA(String path) {
    return nivelDeAcceso().puedeAccederA(path);
  }

  @Override
  public Map<String, Object> datosDelHome(Request request) {
    return null;
  }

  @Override
  public void limpiarSession(Request request) {
    request.session().attribute("cuenta",null);
    request.session().attribute("miembro", null);
    request.session().attribute("trayecto", null);
  }

  public Map<String, Object> datosDelHome() {
    return new HashMap<>();
  }

}
