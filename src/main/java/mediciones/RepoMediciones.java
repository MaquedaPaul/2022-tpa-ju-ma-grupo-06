package mediciones;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import organizacion.Organizacion;

import java.time.YearMonth;
import java.util.List;

public class RepoMediciones implements WithGlobalEntityManager {
  private static RepoMediciones repoMediciones = null;

  private RepoMediciones() {
  }

  public static RepoMediciones getInstance() {
    if (repoMediciones == null) {
      repoMediciones = new RepoMediciones();
    }
    return repoMediciones;
  }

  public void cargarMedicion(Medicion medicion) {
    entityManager().persist(medicion);
    entityManager().refresh(medicion);
  }

  public int medicionesTotales() {
    return entityManager()
        .createQuery("From Medicion ")
        .getResultList()
        .size();
  }

  @SuppressWarnings("unchecked")
  public List<Medicion> medicionesDe(Organizacion organizacion) {
    return entityManager()
        .createQuery("FROM Medicion WHERE organizacion.id = :id")
        .setParameter("id", organizacion.getId())
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<Medicion> getMedicionesEntre(YearMonth inicio, YearMonth fin) {
    return entityManager().createQuery("FROM Medicion WHERE mes BETWEEN :mesInicio AND :mesFin"
            + " AND anio BETWEEN :anioInicio AND :anioFin")
        .setParameter("mesInicio", inicio.getMonthValue())
        .setParameter("mesFin", fin.getMonthValue())
        .setParameter("anioInicio", inicio.getYear())
        .setParameter("anioFin", inicio.getYear())
        .getResultList();
  }
}
