package territorio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class AgenteTerritorial {

  @Id
  @GeneratedValue
  private Long id;

  @OneToOne
  SectorTerritorial sector;

  public AgenteTerritorial(SectorTerritorial unSector) {
    this.sector = unSector;
  }

  public AgenteTerritorial() {

  }

  public SectorTerritorial getSectorTerritorial() {
    return sector;
  }
}