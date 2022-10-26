package mediciones;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import organizacion.Organizacion;
import organizacion.periodo.PeriodoMensual;

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
    entityManager().getTransaction().begin();
    entityManager().persist(medicion);
    entityManager().getTransaction().commit();
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

  /*
   * Las MEDICIONES se guardan con una fecha generada con los valores mes y año leidos del archivo csv,
   * pero como no incluye el dia arbitrariamente setteo el dia en 15
   *
   * Los PERIODOS tienen el dia de su atributo fecha setteado en 1.
   * */
  @SuppressWarnings("unchecked")
  public List<Medicion> getMedicionesEntre(PeriodoMensual inicio, PeriodoMensual fin) {
    return entityManager().createQuery("FROM Medicion " +
            "WHERE Medicion.periodicidad.fecha BETWEEN :fechaInicio AND :fechaFin")
        .setParameter("fechaInicio", inicio.getFecha())
        .setParameter("fechaFin", fin.getFecha())
        .getResultList();
  }
}
