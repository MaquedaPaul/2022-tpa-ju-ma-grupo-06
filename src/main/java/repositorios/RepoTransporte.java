package repositorios;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import transporte.Transporte;

import java.util.List;
import java.util.stream.Collectors;

public enum RepoTransporte implements WithGlobalEntityManager {
  Instance;

  public List<Transporte> queryTransportesPor(String tipoTransporte) {
    return entityManager().createQuery("from Transporte where TRANSPORTE_UTILIZADO = :d")
        .setParameter("d", tipoTransporte).getResultList();
  }

  public List<String> getTiposTransportes() {
    return entityManager().createNativeQuery("SELECT DISTINCT TRANSPORTE_UTILIZADO FROM transporte").getResultList();
  }

  /**
   * Devuelve el transporte con el nombre indicado, <strong>se debe comprobar que exista un transporte con ese nombre antes de utilizarlo</strong>
   * @param nombre un String del nombre del transporte
   */
  public Transporte getTransporteByName(String nombre) {
    return this.getTransportes()
            .stream()
            .filter(t -> t.getNombre().equals(nombre))
            .collect(Collectors.toList()).get(0);
  }

  public boolean existeTransporte(String nombre) {
    return this.getTransportes()
            .stream()
            .anyMatch(t -> t.getNombre().equals(nombre));
  }

  private List<Transporte> getTransportes() {
    return (List<Transporte>) entityManager().createQuery("FROM Transporte ").getResultList();
  }
}
