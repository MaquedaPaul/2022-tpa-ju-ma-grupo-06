package cuenta;

import spark.Request;

import javax.persistence.Entity;

@Entity
public class AgenteCuenta extends Cuenta {

  public AgenteCuenta() {
  }

  public AgenteCuenta(String usuario, String password) {
    super(usuario, password);
  }

  public String home() {
    return "agenteHome.hbs";
  }

  public String tipoCuenta() {
    return "agente";
  }

  public void guardarEnSesion(Request request) {
    Cuenta agente = RepoCuentas.getInstance().accountByUsername(request.queryParams("user"));
    request.session().attribute("cuenta",agente);
    request.session().attribute("agente",RepoCuentas.getInstance().obtenerAgente(agente));

  }


}
