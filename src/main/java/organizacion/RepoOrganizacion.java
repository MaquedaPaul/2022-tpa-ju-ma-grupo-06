package organizacion;

import java.util.ArrayList;
import java.util.List;

public class RepoOrganizacion {
  public static List<Organizacion> organizaciones = new ArrayList<>();

  private RepoOrganizacion() {
  }

  public List<Organizacion> getOrganizaciones() {
    return organizaciones;
  }

  private void agregarOrganizacion(Organizacion nuevaOrganizacion) {
    organizaciones.add(nuevaOrganizacion);
  }
}
