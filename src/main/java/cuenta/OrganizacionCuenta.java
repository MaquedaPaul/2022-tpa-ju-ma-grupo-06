package cuenta;

import controllers.organizacion.OrganizacionController;
import repositorios.RepoCuentas;
import spark.Request;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class OrganizacionCuenta extends Cuenta {

  protected OrganizacionCuenta() {
  }

  public OrganizacionCuenta(String usuario, String password) {
    super(usuario, password);
  }

  public String home() {
    return "organizacionHcTotalNew.hbs";
  }

  public TipoCuenta nivelDeAcceso() {
    return TipoCuenta.ORGANIZACION;
  }

  @Override
  public void guardarEnSesion(Request request) {
    request.session().attribute("cuenta",this);
    request.session().attribute("organizacion", RepoCuentas.getInstance().obtenerOrganizacion(this));
  }

  @Override
  public boolean puedeAccederA(String path) {
    return nivelDeAcceso().puedeAccederA(path);
  }

  @Override
  public Map<String, Object> datosDelHome(Request request) {
    return OrganizacionController.datosDelHome(request);
  }

  @Override
  public void limpiarSession(Request request) {
    request.session().attribute("cuenta",null);
    request.session().attribute("organizacion", null);
  }

  public Map<String, Object> datosDelHome() {
    return new HashMap<>();
  }


}
