package territorio;

public class AgenteTerritorial {
  SectorTerritorial sector;

  public AgenteTerritorial(SectorTerritorial unSector) {
    this.sector = unSector;
  }

  public SectorTerritorial getSectorTerritorial() {
    return sector;
  }
}