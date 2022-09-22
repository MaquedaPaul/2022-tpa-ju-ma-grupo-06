package organizacion;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import registrohc.RegistroHCOrganizacion;

import java.time.Year;
import java.time.YearMonth;

import java.util.List;

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
  public List<Organizacion> filtrarPorTipoOrganizacion(TipoOrganizacion tipoOrganizacion){
    return entityManager()
            .createQuery("from Organizacion where tipo = :t")
            .setParameter("t",tipoOrganizacion)
            .getResultList();
  }

  public void eliminarOrganizacion(Organizacion organizacion){
    entityManager().remove(organizacion);
  }

  @SuppressWarnings("unchecked")
  public List<HC_Por_Tipo_Organizacion> HCPorTipoOrganizacion(YearMonth fechaInicio, YearMonth fechaFin){

    String consulta = "SELECT o.tipo, SUM(mhc.hcMediciones + mhc.hcMiembros) AS HC_TOTAL" +
            " FROM Organizacion AS o" +
            " LEFT JOIN RegistroHCOrganizacion AS mhc " +
            " ON o.id = mhc.organizacion.id" +
            " WHERE mhc.mesImputacion BETWEEN :mesInicio AND :mesFin" +
            " AND mhc.anioImputacion BETWEEN :anioInicio AND :anioFin" +
            " GROUP BY o.tipo " +
            " ORDER BY 2";

    return entityManager().createQuery(consulta, HC_Por_Tipo_Organizacion.class)
            .setParameter("anioInicio",fechaInicio.getYear())
            .setParameter("anioFin",fechaFin.getYear())
            .setParameter("mesInicio",fechaInicio.getMonthValue())
            .setParameter("mesFin",fechaFin.getMonthValue())
            .getResultList();


  }



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

