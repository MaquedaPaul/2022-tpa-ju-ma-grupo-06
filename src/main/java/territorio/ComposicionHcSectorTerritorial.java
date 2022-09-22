package territorio;

import tipoconsumo.TipoConsumo;

public class ComposicionHcSectorTerritorial {

  private final TipoConsumo tipo;
  private final Long hc;

  public ComposicionHcSectorTerritorial(TipoConsumo tipoConsumo, Long hc) {
    this.hc = hc;
    this.tipo = tipoConsumo;
  }
}
