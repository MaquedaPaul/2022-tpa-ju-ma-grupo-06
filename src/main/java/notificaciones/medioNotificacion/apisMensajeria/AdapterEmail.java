package notificaciones.medioNotificacion.apisMensajeria;

import notificaciones.Contacto;
import notificaciones.medioNotificacion.MedioNotificador;

import java.time.LocalDate;
import java.util.List;

public class AdapterEmail implements MedioNotificador {

  private final String asunto;
  private String cuerpo;
  private String url;

  public AdapterEmail(String asunto) {
    this.asunto = asunto;
  }

  @Override
  public void enviarATodos(List<Contacto> contactos) {
    contactos.forEach(this::enviarA);
  }

  public void enviarA(Contacto contacto) {
    //APIMail.mailTo(...);
  }

  @Override
  public void setMensajePlantilla(String mensaje) {
    this.cuerpo = mensaje;
  }

  @Override
  public String getMensajePlantilla(Contacto contacto) {
    String mensajePersonalizado = this.cuerpo.replace("*NOMBRE_CONTACTO*", contacto.getNombreContacto());
    mensajePersonalizado = mensajePersonalizado.replace("*ORGANIZACION*", contacto.organizacion());
    mensajePersonalizado = mensajePersonalizado.replace("*MES*", LocalDate.now().getMonth().toString());
    mensajePersonalizado = mensajePersonalizado.replace("*URL*", this.url);
    return mensajePersonalizado;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getCuerpo() {
    return cuerpo;
  }

  public void setCuerpo(String cuerpo) {
    this.cuerpo = cuerpo;
  }

  public String getAsunto() {
    return asunto.replace("*MES/AÑO*", LocalDate.now().getYear() + "/" + LocalDate.now().getMonth());
  }
}
