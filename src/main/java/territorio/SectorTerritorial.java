package territorio;

import lombok.Getter;
import organizacion.Organizacion;
import organizacion.periodo.GeneradorDePeriodos;
import organizacion.periodo.Periodo;
import organizacion.periodo.PeriodoMensual;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

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

  public List<EvolucionHCSectorTerritorial> reporteEvolucionHC(PeriodoMensual inicio, PeriodoMensual fin) {

    GeneradorDePeriodos gen = new GeneradorDePeriodos();
    List<PeriodoMensual> periodos = gen.generarPeriodosMensualesEntre(inicio, fin);

    return periodos
        .stream()
        .map(this::crearItemDelReporteDeEvolucionHC)
        .collect(Collectors.toList());
  }

  private EvolucionHCSectorTerritorial crearItemDelReporteDeEvolucionHC(PeriodoMensual periodo) {
    return new EvolucionHCSectorTerritorial(this, periodo.getFecha(), this.calcularHC(periodo));
  }

  public double calcularHCEntre(PeriodoMensual inicio, PeriodoMensual fin) {
    return this.getOrganizaciones()
        .stream()
        .mapToDouble(org -> org.calcularHCTotalEntre(inicio, fin))
        .sum();
  }

  public ComposicionHcSectorTerritorial generarItemComposicionHCEntre(PeriodoMensual inicio, PeriodoMensual fin) {
    return new ComposicionHcSectorTerritorial(this,
        this.calcularHCMiembrosEntre(inicio, fin),
        this.calcularHCMedicionesEntre(inicio, fin));
  }

  private double calcularHCMiembrosEntre(PeriodoMensual inicio, PeriodoMensual fin) {
    return this.getOrganizaciones()
        .stream()
        .mapToDouble(org -> org.calcularHCMiembrosEntre(inicio, fin))
        .sum();
  }

  private double calcularHCMedicionesEntre(PeriodoMensual inicio, PeriodoMensual fin) {
    return this.getOrganizaciones()
        .stream()
        .mapToDouble(org -> org.calcularHCMedicionesEntre(inicio, fin))
        .sum();
  }
}
