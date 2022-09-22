package organizacion;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import registrohc.RegistroHCOrganizacion;
import registrohc.RepoMedicionesHCOrganizaciones;

import java.time.YearMonth;
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
    entityManager().persist(nuevaOrganizacion);
    entityManager().refresh(nuevaOrganizacion);
  }

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
    entityManager().remove(organizacion);
  }

  public List<RegistroHCOrganizacion> evolucionHCTotal(Organizacion organizacion, YearMonth inicio, YearMonth fin) {

    return RepoMedicionesHCOrganizaciones.getInstance().getRegistros(organizacion, inicio, fin);
  }

  @SuppressWarnings("unchecked")
  public List<HC_Por_Tipo_Organizacion> HCPorTipoOrganizacion(YearMonth fechaInicio, YearMonth fechaFin) {

    List<TipoOrganizacion> tipos = entityManager().createQuery("SELECT DISTINCT tipo FROM Organizacion").getResultList();

    List<Organizacion> organizaciones = this.getOrganizaciones();

    return tipos.stream()
          .map(tipo -> new HC_Por_Tipo_Organizacion(organizaciones
            .stream()
            .filter(org -> org.getTipo() == tipo)
            .mapToLong(org -> RepoMedicionesHCOrganizaciones
                    .getInstance()
                    .getRegistros(org, fechaInicio, fechaFin)
            .stream().mapToLong(RegistroHCOrganizacion::hcTotal).sum())
            .sum()
            , tipo))
        .collect(Collectors.toList());
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

