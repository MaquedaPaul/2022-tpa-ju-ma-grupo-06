package notificaciones;

import notificaciones.medioNotificacion.MedioNotificador;
import organizacion.RepoOrganizacion;

import java.util.ArrayList;
import java.util.List;

public class Notificador {

  private final ArrayList<MedioNotificador> medios = new ArrayList<>();

  public void enviarGuias() {
    this.medios().forEach(medio -> medio.enviarATodos(this.contactosDeLasOrganizaciones()));
  }

  public List<Contacto> contactosDeLasOrganizaciones() {
    List<Contacto> contactosTotales = new ArrayList<>();
    RepoOrganizacion
        .getInstance()
        .getOrganizaciones()
        .forEach(org -> contactosTotales.addAll(org.getContactos()));

    return contactosTotales;
  }

  public void agregarMedios(MedioNotificador medio) {
    medios.add(medio);
  }

  public ArrayList<MedioNotificador> medios() {
    return medios;
  }

}
