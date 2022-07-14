package notificaciones.medioNotificacion;

import notificaciones.Contacto;

import java.util.List;

public interface MedioNotificador {
  void enviarATodos(List<Contacto> contactos);
  void setMensajeEnvioStandar(String mensaje);
  void enviarA(Contacto contacto);
  String getMensajeEnvioPara(Contacto contacto);
}
