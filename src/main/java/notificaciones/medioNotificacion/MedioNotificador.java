package notificaciones.medioNotificacion;

import notificaciones.Contacto;

import java.util.List;

public interface MedioNotificador {


  void enviarATodos(List<Contacto> contactos);

  void enviarA(Contacto contacto);

  String getUrl();

  void setUrl(String url);

  void setMensaje(String mensaje);

  String getMensaje();

  String mensajePersonalizadoPara(Contacto contacto);

  void setAsunto(String asunto);

  String getAsunto();
}
