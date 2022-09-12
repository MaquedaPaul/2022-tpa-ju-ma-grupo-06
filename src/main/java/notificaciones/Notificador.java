package notificaciones;

import notificaciones.medioNotificacion.MedioNotificador;
import organizacion.RepoOrganizacion;

import java.util.ArrayList;

public class Notificador {
  private final ArrayList<MedioNotificador> medios = new ArrayList<>();

  public void organizacionesNotifiquen() {
    RepoOrganizacion
        .getInstance()
        .getOrganizaciones()
        .forEach(org -> org.notificarContacto(this.medios()));
  }

  public void agregarMedios(MedioNotificador medio) {
    medios.add(medio);
  }

  public ArrayList<MedioNotificador> medios() {
    return medios;
  }

}
