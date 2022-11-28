package cuenta;

import java.util.Arrays;

public enum TipoCuenta {

  MIEMBRO() {
    @Override
    public boolean puedeAccederA(String path) {
      String[] pathsValidos = {""};
      return Arrays.asList(pathsValidos).contains(path);
    }
  }, ORGANIZACION() {
    @Override
    public boolean puedeAccederA(String path) {
      String[] pathsValidos = {""};
      return Arrays.asList(pathsValidos).contains(path);
    }
  }, AGENTE() {
    @Override
    public boolean puedeAccederA(String path) {
      String[] pathsValidos = {
          ".*/home",
          ".*/home/composicion-hc",
          ".*/home/composicion-hc/grafico",
          ".*/home/evolucion-hc",
          ".*/home/evolucion-hc/grafico",
          ".*/home/organizaciones",
          ".*/home/hc-total",
          ".*/home/organizaciones/hc-tipo-organizacion/.*",
          ".*/home/organizaciones/.*/evolucion-hc/consulta",
          ".*/home/organizaciones/.*/evolucion-hc/grafico",
          ".*/home/organizaciones/.*/composicion-hc/consulta",
          ".*/home/organizaciones/.*/composicion-hc/grafico"
      };
      return Arrays.stream(pathsValidos).anyMatch(path::matches);
    }
  };

  public abstract boolean puedeAccederA(String path);
}
