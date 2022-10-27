package territorio;

import cuenta.AgenteCuenta;

import javax.persistence.*;

@Entity
public class AgenteTerritorial {

  @Id
  @GeneratedValue
  private Long id;

  @OneToOne(cascade = CascadeType.PERSIST)
  SectorTerritorial sector;

  @OneToOne(cascade = CascadeType.PERSIST)
  AgenteCuenta cuenta;

  public AgenteTerritorial(SectorTerritorial unSector) {
    this.sector = unSector;
  }

  public AgenteTerritorial() {

  }

  public SectorTerritorial getSectorTerritorial() {
    return sector;
  }

  public void setCuenta(AgenteCuenta cuenta) {
    this.cuenta = cuenta;
  }
}