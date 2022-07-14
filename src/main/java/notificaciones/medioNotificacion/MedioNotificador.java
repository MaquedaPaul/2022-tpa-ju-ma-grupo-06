package notificaciones.medioNotificacion;

import notificaciones.Contacto;

import java.util.List;

public interface MedioNotificador {
  public void enviarATodos(List<Contacto> contactos);

  public void setMensajeEnvioStandar(String mensaje);

  public String getMensajeEnvioPara(Contacto contacto);
}
