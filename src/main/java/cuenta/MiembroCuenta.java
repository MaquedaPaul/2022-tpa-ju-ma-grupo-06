package cuenta;

import miembro.Miembro;

import javax.persistence.Entity;

@Entity
public class MiembroCuenta extends Cuenta {

  public MiembroCuenta() {
  }

  public MiembroCuenta(String usuario, String password) {
    super(usuario, password);
  }

  public String getTemplate() {
    return "miembroHome.hbs";
  }

  public String tipoCuenta() {
    return "miembro";
  }
}
