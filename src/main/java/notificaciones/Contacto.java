package notificaciones;

import organizacion.Miembro;
import organizacion.Organizacion;

public class Contacto {

  private final Organizacion organizacion;
  private String nombreContacto;
  private String mail;
  private String nroCelular;

  public Contacto(Organizacion organizacion, String nombreContacto, String mail, String nroCelular) {
    this.organizacion = organizacion;
    this.mail = mail;
    this.nroCelular = nroCelular;
    this.nombreContacto = nombreContacto;
  }

  public String getNroCelular() {
    return nroCelular;
  }

  public void setNroCelular(String nroCelular) {
    this.nroCelular = nroCelular;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String organizacion() {
    return organizacion.getRazonSocial();
  }

  public String getNombreContacto() {
    return nombreContacto;
  }
}
