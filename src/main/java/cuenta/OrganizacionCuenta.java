package cuenta;

import spark.Request;

import javax.persistence.Entity;
import java.util.HashMap;
import java.util.Map;

@Entity
public class OrganizacionCuenta extends Cuenta {

  public OrganizacionCuenta() {
  }

  public OrganizacionCuenta(String usuario, String password) {
    super(usuario, password);
  }

  public String home() {
    return "organizacionHome.hbs";
  }

  public TipoCuenta nivelDeAcceso() {
    return TipoCuenta.ORGANIZACION;
  }

  @Override
  public void guardarEnSesion(Request request) {

  }

  @Override
  public boolean puedeAccederA(String path) {
    return false;
  }

  @Override
  public Map<String, Object> datosDelHome(Request request) {
    return null;
  }

  public Map<String, Object> datosDelHome() {
    return new HashMap<>();
  }
}
