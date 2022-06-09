package admin;

import java.util.ArrayList;
import java.util.List;

public class RepoFactoresEmision {
  static List<FactorEmision> factoresEmision = new ArrayList<>();

  public RepoFactoresEmision(){
  };

  void incorporarFactor(FactorEmision nuevoFactor) {
    factoresEmision.add(nuevoFactor);
  }

  void modificarFactorEmicion(Double nuevoValor, int posicion){
    factoresEmision.get(posicion).setValor(nuevoValor);
  }

  public List<FactorEmision> getFactoresEmision() {
    return factoresEmision;
  }
}
