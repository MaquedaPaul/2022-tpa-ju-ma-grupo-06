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

  public TipoCuenta tipoCuenta() {
    return TipoCuenta.ORGANIZACION;
  }

  @Override
  public void guardarEnSesion(Request request) {

  }
}
