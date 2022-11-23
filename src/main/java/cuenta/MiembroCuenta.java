package cuenta;

import spark.Request;

import javax.persistence.Entity;

@Entity
public class MiembroCuenta extends Cuenta {

  public MiembroCuenta() {
  }

  public MiembroCuenta(String usuario, String password) {
    super(usuario, password);
  }

  public String home() {
    return "miembroHome.hbs";
  }

  public TipoCuenta tipoCuenta() {
    return TipoCuenta.MIEMBRO;
  }

  @Override
  public void guardarEnSesion(Request request) {

  }
}
