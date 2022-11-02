package territorio;

import lombok.Getter;
import organizacion.Organizacion;
import organizacion.periodo.Periodo;
import organizacion.periodo.PeriodoMensual;

import javax.persistence.*;
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

  public double calcularHC(Periodo periodo) {
    return organizaciones.stream().mapToDouble(unaOrg -> unaOrg.calcularHCTotal(periodo)).sum();
  }

  public void incorporarOrganizacion(Organizacion organizacion) {
    organizaciones.add(organizacion);
  }


  public double calcularHCEntre(PeriodoMensual inicio, PeriodoMensual fin) {
    return this.getOrganizaciones()
        .stream()
        .mapToDouble(org -> org.calcularHCTotalEntre(inicio, fin))
        .sum();
  }


  public double calcularHCMiembrosEntre(PeriodoMensual inicio, PeriodoMensual fin) {
    return this.getOrganizaciones()
        .stream()
        .mapToDouble(org -> org.calcularHCMiembrosEntre(inicio, fin))
        .sum();
  }

  public double calcularHCMedicionesEntre(PeriodoMensual inicio, PeriodoMensual fin) {
    return this.getOrganizaciones()
        .stream()
        .mapToDouble(org -> org.calcularHCMedicionesEntre(inicio, fin))
        .sum();
  }
}
