package admin;

import global.Unidad;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Administrador {

  @Id
  String usuario;
  String password;

  public Administrador() {
  }

  public Administrador(String unUsuario, String unPassword) {
    this.usuario = unUsuario;
    this.password = unPassword;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  void crearFactorEmision(double valor, Unidad unidadDivisible) {
    RepoFactoresEmision.getInstance().incorporarFactor(new FactorEmision(valor, unidadDivisible));
  }

  void modificarFactorDeEmision(FactorEmision unFactor, double valor) {
    RepoFactoresEmision.getInstance().modificarFactorEmicion(unFactor, valor);
  }
}



