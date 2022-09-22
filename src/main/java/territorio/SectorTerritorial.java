package territorio;

import lombok.Getter;
import organizacion.Organizacion;

import javax.persistence.*;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

@Getter
@Entity
public class SectorTerritorial {


  @Id
  @GeneratedValue
  private Long id;

  @OneToMany
  @JoinColumn(name = "sector_territorial_id")
  List<Organizacion> organizaciones;

  @Enumerated
  TipoSectorTerritorial tipoSectorTerritorial;

  public SectorTerritorial(
      List<Organizacion> organizaciones,
      TipoSectorTerritorial tipoSectorTerritorial) {
    this.organizaciones = organizaciones;
    this.tipoSectorTerritorial = tipoSectorTerritorial;
  }

  public SectorTerritorial() {

  }

  public double totalHC(YearMonth periodo) {
    return organizaciones.stream().mapToDouble(unaOrg -> unaOrg.calcularHCTotal(periodo)).sum();
  }

  public double totalHC(Year periodo) {
    return organizaciones.stream().mapToDouble(unaOrg -> unaOrg.calcularHCTotal(periodo)).sum();
  }

  public void incorporarOrganizacion(Organizacion organizacion) {
    organizaciones.add(organizacion);
  }

}
