package cuenta;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "cuentaId")
public abstract class Cuenta {

  @Id
  String usuario;
  String password;

  public Cuenta(){
  }

  public Cuenta(String usuario, String password) {
    this.usuario = usuario;
    this.password = password;
  }

  public String getUsuario() {
    return usuario;
  }

  public String getPassword() {
    return password;
  }

  public abstract String getTemplate();
}
