package territorio;

import lombok.Getter;

import java.time.YearMonth;

@Getter
public class HCPorSectorTerritorial {

  private final Long hcTotal;
  private final SectorTerritorial sectorTerritorial;
  private final YearMonth fechaImputacion;

  public HCPorSectorTerritorial(Long hc, SectorTerritorial sector, YearMonth fechaImputacion) {
    this.hcTotal = hc;
    this.sectorTerritorial = sector;
    this.fechaImputacion = fechaImputacion;
  }

}
