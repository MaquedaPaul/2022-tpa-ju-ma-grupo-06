package notificaciones.medioNotificacion;

import notificaciones.Contacto;

import java.util.List;

public interface MedioNotificador {


  public void enviarATodos(List<Contacto> contactos);

  public void enviarA(Contacto contacto);

  void setMensajePlantilla(String mensaje);

  String getMensajePlantilla(Contacto contacto);

}
