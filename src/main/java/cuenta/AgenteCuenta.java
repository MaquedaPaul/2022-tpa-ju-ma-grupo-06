package cuenta;

import javax.persistence.Entity;

@Entity
public class AgenteCuenta extends Cuenta {

  public AgenteCuenta() {
  }

  public AgenteCuenta(String usuario, String password) {
    super(usuario, password);
  }

  public String getTemplate() {
    return "agenteHome.hbs";
  }

  public String tipoCuenta() {
    return "agente";
  }
}
