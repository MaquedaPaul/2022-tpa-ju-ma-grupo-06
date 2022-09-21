package admin;

import java.util.ArrayList;
import java.util.List;

public class RepoFactoresEmision {
  private List<FactorEmision> factoresEmision = new ArrayList<>();
  private static RepoFactoresEmision repoFactoresEmision = null;

  private RepoFactoresEmision() {
  }

  public static RepoFactoresEmision getInstance() {
    if (repoFactoresEmision == null) {
      repoFactoresEmision = new RepoFactoresEmision();
    }
    return repoFactoresEmision;
  }

  void incorporarFactor(FactorEmision nuevoFactor) {
    factoresEmision.add(nuevoFactor);
  }

  void modificarFactorEmicion(FactorEmision unFactor, double nuevoValor) {
    unFactor.setValor(nuevoValor);
  }

  public List<FactorEmision> getFactoresEmision() {
    return factoresEmision;
  }

  public int factoresTotales() {
    return factoresEmision.size();
  }

  public void matate() {
    this.factoresEmision = new ArrayList<>();
  }
}
