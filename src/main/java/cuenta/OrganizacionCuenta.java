package cuenta;

import organizacion.Organizacion;

import javax.persistence.Entity;

@Entity
public class OrganizacionCuenta extends Cuenta {

  public OrganizacionCuenta() {
  }

  public OrganizacionCuenta(String usuario, String password) {
    super(usuario, password);
  }

  public String getTemplate() {
    return "home-organizacion.hbs";
  }
}
