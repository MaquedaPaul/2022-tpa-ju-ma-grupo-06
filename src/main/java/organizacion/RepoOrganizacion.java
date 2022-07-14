package organizacion;

import java.util.ArrayList;
import java.util.List;

public class RepoOrganizacion {
  private final List<Organizacion> organizaciones = new ArrayList<>();
  private static RepoOrganizacion repoOrganizacion = null;

  private RepoOrganizacion() {
  }

  public static RepoOrganizacion getInstance() {
    if (repoOrganizacion == null) {
      repoOrganizacion = new RepoOrganizacion();
    }
    return repoOrganizacion;
  }

  public void agregarOrganizacion(Organizacion nuevaOrganizacion) {
    organizaciones.add(nuevaOrganizacion);
  }

  public List<Organizacion> getOrganizaciones() {
    return organizaciones;
  }

}
