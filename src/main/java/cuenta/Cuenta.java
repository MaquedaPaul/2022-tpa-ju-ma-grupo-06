package cuenta;

import lombok.Getter;
import lombok.Setter;
import spark.Request;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "cuenta")
@DiscriminatorColumn(name = "tipo_cuenta")
public abstract class Cuenta {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String usuario;

  private String password;

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

  public abstract String home();

  public abstract TipoCuenta tipoCuenta();

  public abstract void guardarEnSesion(Request request);
}
