package organizacion.repositorio;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import organizacion.Organizacion;
import organizacion.Sector;
import organizacion.TipoOrganizacion;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RepoOrganizacion implements WithGlobalEntityManager {

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
    entityManager().getTransaction().begin();
    entityManager().persist(nuevaOrganizacion);
    entityManager().getTransaction().commit();
  }

  public boolean estaPersistido(Organizacion org) {
    return entityManager().contains(org);
  }

  @SuppressWarnings("unchecked")
  public List<Organizacion> getOrganizaciones() {
    return entityManager().createQuery("from Organizacion").getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<Organizacion> getOrganizacionesDelTipo(TipoOrganizacion tipoOrganizacion) {
    return entityManager()
        .createQuery("from Organizacion where tipo = :t")
        .setParameter("t", tipoOrganizacion)
        .getResultList();
  }

  public void eliminarOrganizacion(Organizacion organizacion) {
    entityManager().getTransaction().begin();
    entityManager().remove(organizacion);
    entityManager().getTransaction().commit();
  }

  public Set<Sector> obtenerTodosLosSectores() {
    return getOrganizaciones().stream().flatMap(organizacion -> organizacion.generarSectoresVacios().stream()).collect(Collectors.toSet());
  }
}

