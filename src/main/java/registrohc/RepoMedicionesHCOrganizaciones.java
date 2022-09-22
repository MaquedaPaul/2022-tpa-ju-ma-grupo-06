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
    registros.forEach(this::persistir);
  }

  public void persistir(RegistroHCOrganizacion registro) {
    entityManager().persist(registro);
  }

  @SuppressWarnings("unchecked")
  public List<RegistroHCOrganizacion> getRegistros(Organizacion org, YearMonth inicio, YearMonth fin) {

    String consulta = "FROM RegistroHCOrganizacion WHERE id = :id " +
        "AND mesImputacion BETWEEN :mesInicio AND :mesFin" +
        " AND anioImputacion BETWEEN :anioInicio AND :anioFin";

    return entityManager().createQuery(consulta).setParameter("id", org.getId())
        .setParameter("mesInicio", inicio.getMonthValue())
        .setParameter("mesFin", fin.getMonthValue())
        .setParameter("anioInicio", inicio.getYear())
        .setParameter("anioFin", inicio.getYear())
        .getResultList();
  }
}
