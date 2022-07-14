package notificaciones.medioNotificacion.apisMensajeria;

import notificaciones.Contacto;
import notificaciones.medioNotificacion.MedioNotificador;

import java.time.LocalDate;
import java.util.List;

public class AdapterWhatsapp implements MedioNotificador {

  private String mensajePlantilla;
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
  public void setMensajePlantilla(String mensaje) {
    this.mensajePlantilla = mensaje;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public String getMensajePlantilla(Contacto contacto) {
    String mensajePersonalizado = this.mensajePlantilla
        .replace("*NOMBRE_CONTACTO*", contacto.getNombreContacto());

    mensajePersonalizado = mensajePersonalizado
        .replace("*ORGANIZACION*", contacto.organizacion());

    mensajePersonalizado = mensajePersonalizado
        .replace("*MES*", LocalDate.now().getMonth().toString());

    mensajePersonalizado = mensajePersonalizado
        .replace("*URL*", this.url);

    return mensajePersonalizado;
  }
  //HOLA *NOMBRE CONTACTO* DE *ORGANIZACION*
}
