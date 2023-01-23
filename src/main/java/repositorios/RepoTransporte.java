package repositorios;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import transporte.Transporte;

import java.util.List;

public enum RepoTransporte implements WithGlobalEntityManager {
  Instance;

  public List<Transporte> queryTransportesPor(String tipoTransporte) {
    return entityManager().createQuery("from Transporte where TRANSPORTE_UTILIZADO = :d")
        .setParameter("d", tipoTransporte).getResultList();
  }

  public List<String> getTiposTransportes() {
    return entityManager().createNativeQuery("SELECT DISTINCT TRANSPORTE_UTILIZADO FROM transporte").getResultList();
  }

  public Transporte getTransporteByName(String nombre) {
    return (Transporte) entityManager()
        .createQuery("FROM Transporte WHERE Transporte.nombre = :t")
        .setParameter("t",nombre)
        .getResultList()
        .get(0);
  }

  public boolean existeTransporte(String nombre) {
        return !entityManager()
        .createQuery("FROM Transporte WHERE Transporte.nombre = :t")
        .setParameter("t",nombre)
        .getResultList().isEmpty();
  }
}
