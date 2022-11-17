package cuenta;

import spark.Request;

import javax.persistence.Entity;

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

  public String tipoCuenta() {
    return "organizacion";
  }

  @Override
  public void guardarEnSesion(Request request) {

  }
}
