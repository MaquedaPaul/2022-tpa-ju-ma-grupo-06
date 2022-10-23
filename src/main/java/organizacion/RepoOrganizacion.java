package organizacion;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

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
  //em.persist(org);

  //new medicion(org);

  public boolean estaPersistido(Organizacion org) {
    return entityManager().contains(org);
  }

  @SuppressWarnings("unchecked")
  public List<Organizacion> getOrganizaciones() {
    return entityManager().createQuery("from Organizacion").getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<Organizacion> filtrarPorTipoOrganizacion(TipoOrganizacion tipoOrganizacion) {
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

  List<Organizacion> obtenerOrganizacionesPorTipo(List<Organizacion> organizaciones, TipoOrganizacion tipo){
    return organizaciones.stream().filter(org -> org.getTipo() ==tipo).collect(Collectors.toList());
  }


/*
COMPOSICIÓN HC TOTAL DE UNA ORGANIZACION

En principio agrupar y sumar HC Combustion Fija




*/



  /*
  * HC TOTAL POR SECTOR TERRITORIAL (FechaInicio, FechaFin, PERIORICIDAD)
obtengo los sectores territoriales
PARA CADA SECTOR:
	obtengo las organizaciones pertenecientes al sector
	calculo el hc de cada organizacion
	sumo los resultados
SECTOR TERRITORIAL: HC_TOTAL: FECHA INICIO : FECHA FIN

  *
  * */
}

