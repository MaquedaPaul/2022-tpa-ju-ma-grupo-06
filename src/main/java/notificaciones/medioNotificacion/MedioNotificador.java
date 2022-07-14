package notificaciones.medioNotificacion;

import notificaciones.Contacto;

import java.util.List;

public interface MedioNotificador {
  void enviarATodos(List<Contacto> contactos);
  void setMensajePlantilla(String mensaje);
  void enviarA(Contacto contacto);
  String getMensajePlantilla(Contacto contacto);
}
