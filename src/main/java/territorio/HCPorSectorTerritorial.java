package territorio;

import lombok.Getter;

@Getter
public class HCPorSectorTerritorial {

  private final double hcTotal;
  private final SectorTerritorial sectorTerritorial;

  public HCPorSectorTerritorial(double hc, SectorTerritorial sector) {
    this.hcTotal = hc;
    this.sectorTerritorial = sector;
  }

}
