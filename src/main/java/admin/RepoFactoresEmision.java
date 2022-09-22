package admin;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;

public class RepoFactoresEmision implements WithGlobalEntityManager {

  private static RepoFactoresEmision repoFactoresEmision = null;

  public RepoFactoresEmision() {
  }

  public static RepoFactoresEmision getInstance() {
    if (repoFactoresEmision == null) {
      repoFactoresEmision = new RepoFactoresEmision();
    }
    return repoFactoresEmision;
  }

  void incorporarFactor(FactorEmision nuevoFactor) {
    entityManager().persist(nuevoFactor);
    entityManager().refresh(nuevoFactor);
  }

  void modificarFactorEmicion(FactorEmision unFactor, double nuevoValor) {
    unFactor.setValor(nuevoValor);
  }

  @SuppressWarnings("unchecked")
  public List<FactorEmision> getFactoresEmision() {
    return entityManager().createQuery("from FactorEmision").getResultList();
  }

  public int factoresTotales() {
    return getFactoresEmision().size();
  }

}
