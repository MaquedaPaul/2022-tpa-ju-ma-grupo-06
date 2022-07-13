package admin;

import java.util.ArrayList;
import java.util.List;

public class RepoFactoresEmision {
  private final List<FactorEmision> factoresEmision = new ArrayList<>();
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

  void modificarFactorEmicion(Double nuevoValor, int posicion) {
    factoresEmision.get(posicion).setValor(nuevoValor);
  }

  public List<FactorEmision> getFactoresEmision() {
    return factoresEmision;
  }

  public int factoresTotales() {
    return factoresEmision.size();
  }
}
