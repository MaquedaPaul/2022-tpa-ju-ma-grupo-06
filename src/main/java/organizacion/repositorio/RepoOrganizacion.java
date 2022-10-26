package organizacion.repositorio;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import organizacion.Organizacion;
import organizacion.TipoOrganizacion;
import organizacion.periodo.PeriodoMensual;

import java.util.Arrays;
import java.util.List;
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

  public List<HCPorTipoOrganizacion> reporteHCPorTipoOrganizacion(PeriodoMensual inicio, PeriodoMensual fin) {

    List<TipoOrganizacion> tipos = Arrays.asList(TipoOrganizacion.values());

    return tipos
        .stream()
        .map(tipo -> this.calcularHCDelTipoDeOrganizacion(tipo, inicio, fin))
        .collect(Collectors.toList());
  }

  private HCPorTipoOrganizacion calcularHCDelTipoDeOrganizacion(TipoOrganizacion tipo, PeriodoMensual inicio, PeriodoMensual fin) {

    double total = this.getOrganizacionesDelTipo(tipo)
        .stream()
        .mapToDouble(org -> org.calcularHCTotalEntre(inicio, fin))
        .sum();

    return new HCPorTipoOrganizacion(total, tipo);
  }

}

