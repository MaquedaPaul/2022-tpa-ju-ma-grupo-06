package admin;

import lombok.Getter;
import lombok.Setter;
import repositorios.RepoFactoresEmision;

import javax.persistence.*;

@Entity
@Getter@Setter
@Table(name = "Administrador")
public class Administrador {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "usuario")
  private String usuario;

  @Column(name = "password")
  private String password;

  public Administrador() {
  }

  public Administrador(String unUsuario, String unPassword) {
    this.usuario = unUsuario;
    this.password = unPassword;
  }

  void crearFactorEmision(FactorEmision unFactor) {
    RepoFactoresEmision.getInstance().incorporarFactor(unFactor);
  }

  void modificarFactorDeEmision(FactorEmision unFactor, double nuevoValor) {
    RepoFactoresEmision.getInstance().modificarFactorEmision(unFactor, nuevoValor);
  }
}



