package registrohc;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import organizacion.Organizacion;

import java.time.YearMonth;
import java.util.List;

public class RepoMedicionesHCOrganizaciones implements WithGlobalEntityManager {

  private static RepoMedicionesHCOrganizaciones instance;

  private RepoMedicionesHCOrganizaciones() {

  }

  public static RepoMedicionesHCOrganizaciones getInstance() {
    if (instance == null) {
      instance = new RepoMedicionesHCOrganizaciones();
    }
    return instance;
  }

  public void persistirTodas(List<RegistroHCOrganizacion> registros) {
    registros.forEach(registro -> this.persistir(registro));
  }

  public void persistir(RegistroHCOrganizacion registro) {
    entityManager().persist(registro);
  }

  @SuppressWarnings("unchecked")
  public List<RegistroHCOrganizacion> evolucionHCOrganizacion(Organizacion organizacion,
                                                              YearMonth fechaInicio,
                                                              YearMonth fechaFin) {
    String consulta = " FROM RegistroHCOrganizacion AS rhc" +
            " WHERE rhc.organizacion = :id" +
            " AND rhc.mesImputacion BETWEEN :mesInicio AND :mesFin " +
            " AND rhc.anioImputacion BETWEEN :anioInicio AND :anioFin " +
            " ORDER BY rhc.anioImputacion, rhc.mesImputacion DESC ";

    return entityManager().createQuery(consulta)
            .setParameter("id",organizacion.getId())
            .setParameter("mesInicio",fechaInicio.getMonthValue())
            .setParameter("mesFin",fechaFin.getMonthValue())
            .setParameter("anioInicio", fechaInicio.getYear())
            .setParameter("anioFin",fechaFin.getYear()).getResultList();
  }

}
