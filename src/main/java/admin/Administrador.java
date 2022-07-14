package admin;

import global.Unidad;

public class Administrador {
  String usuario;
  String password;

  public Administrador(String unUsuario, String unPassword) {
    this.usuario = unUsuario;
    this.password = unPassword;
  }

  public String getUsuario() {
    return usuario;
  }

  public String getPassword() {
    return password;
  }

  void crearFactorEmision(double valor, Unidad unidadDivisible) {
    RepoFactoresEmision.getInstance().incorporarFactor(new FactorEmision(valor, unidadDivisible));
  }

  void modificarFactorDeEmision(FactorEmision unFactor, double valor) {
    RepoFactoresEmision.getInstance().modificarFactorEmicion(unFactor, valor);
  }
}



