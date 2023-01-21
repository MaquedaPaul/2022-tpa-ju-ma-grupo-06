package notificaciones;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter@Setter
public class Contacto {

  @Id
  @GeneratedValue
  private Long id;

  private String nombreContacto;
  private String mail;
  private String nroCelular;

  protected Contacto() {}

  public Long getId() {
    return id;
  }

  public Contacto(
      String nombreContacto,
      String mail,
      String nroCelular) {
    this.mail = mail;
    this.nroCelular = nroCelular;
    this.nombreContacto = nombreContacto;
  }

}
