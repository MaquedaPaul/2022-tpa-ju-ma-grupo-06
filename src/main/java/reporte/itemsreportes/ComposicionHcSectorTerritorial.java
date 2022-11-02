package reporte.itemsreportes;

import territorio.SectorTerritorial;

public class ComposicionHcSectorTerritorial {

  private final SectorTerritorial sectorTerritorial;
  private final double hcMiembros;
  private final double hcMediciones;


  public ComposicionHcSectorTerritorial(SectorTerritorial sectorTerritorial, double hcMiembros, double hcMediciones) {
    this.sectorTerritorial = sectorTerritorial;
    this.hcMiembros = hcMiembros;
    this.hcMediciones = hcMediciones;
  }
}
