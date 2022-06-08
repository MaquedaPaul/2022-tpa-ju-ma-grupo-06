package admin;

import java.util.ArrayList;
import java.util.List;

public class RepoFactoresEmision {
  List<FactorEmision> factoresEmision = new ArrayList<>();

  void incorporarFactor(FactorEmision nuevoFactor) {
    factoresEmision.add(nuevoFactor);
  }

  public List<FactorEmision> getFactoresEmision() {
    return factoresEmision;
  }
}
