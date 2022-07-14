package notificaciones.medioNotificacion.apisMensajeria;

import notificaciones.Contacto;
import notificaciones.medioNotificacion.MedioNotificador;

import java.time.LocalDate;
import java.util.List;

public class AdapterWhatsapp implements MedioNotificador {

  private String mensaje;
  private String url;
  //private ApiWhatsapp apiWhatsapp;

  @Override
  public void enviarATodos(List<Contacto> contactos) {
    contactos.forEach(this::enviarA);
  }

  public void enviarA(Contacto contacto) {
    ////apiWhatsapp.enviarA(contacto, mensaje);
  }

  @Override
  public void setMensajeEnvioStandar(String mensaje) {
    this.mensaje = mensaje;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public String getMensajeEnvioPara(Contacto contacto) {
    String mensajePersonalizado = this.mensaje.replace("*NOMBRE_CONTACTO*", contacto.getNombreContacto());
    mensajePersonalizado = mensajePersonalizado.replace("*ORGANIZACION*", contacto.organizacion());
    mensajePersonalizado = mensajePersonalizado.replace("*MES*", LocalDate.now().getMonth().toString());
    mensajePersonalizado = mensajePersonalizado.replace("*URL*", this.url);
    return mensajePersonalizado;
  }
  //HOLA *NOMBRE CONTACTO* DE *ORGANIZACION*
}
