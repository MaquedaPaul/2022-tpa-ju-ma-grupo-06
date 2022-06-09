package organizacion;

import java.util.ArrayList;
import java.util.List;

public class RepoOrganizacion {
  List<Organizacion> organizaciones = new ArrayList<>();

  private RepoOrganizacion() {
  }

  public List<Organizacion> getOrganizaciones() {
    return organizaciones;
  }

  void agregarOrganizacion(Organizacion nuevaOrganizacion) {
    organizaciones.add(nuevaOrganizacion);
  }
}
