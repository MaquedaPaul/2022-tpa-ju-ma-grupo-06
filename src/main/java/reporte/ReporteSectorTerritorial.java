package reporte;

import organizacion.periodo.GeneradorDePeriodos;
import organizacion.periodo.PeriodoMensual;
import reporte.itemsreportes.ComposicionHcSectorTerritorial;
import reporte.itemsreportes.EvolucionHCSectorTerritorial;
import territorio.HCPorSectorTerritorial;
import territorio.RepoSectorTerritorial;
import territorio.SectorTerritorial;

import java.util.List;
import java.util.stream.Collectors;

public class ReporteSectorTerritorial {

  private final RepoSectorTerritorial repoSectorTerritorial;

  public ReporteSectorTerritorial(RepoSectorTerritorial repo) {
    this.repoSectorTerritorial = repo;
  }

  public List<ComposicionHcSectorTerritorial> reporteComposicionHC(PeriodoMensual inicio, PeriodoMensual fin) {

    return repoSectorTerritorial.getSectoresTerritoriales()
        .stream()
        .map(sector -> this.generarItemComposicionHCEntre(sector, inicio, fin))
        .collect(Collectors.toList());
  }

  public ComposicionHcSectorTerritorial generarItemComposicionHCEntre(SectorTerritorial sector, PeriodoMensual inicio, PeriodoMensual fin) {
    return new ComposicionHcSectorTerritorial(sector,
        sector.calcularHCMiembrosEntre(inicio, fin),
        sector.calcularHCMedicionesEntre(inicio, fin));
  }

  public List<HCPorSectorTerritorial> hcPorSectorTerritorial(PeriodoMensual inicio, PeriodoMensual fin) {

    return repoSectorTerritorial.getSectoresTerritoriales()
        .stream()
        .map(sector -> this.generarItemHCPorSectorTerritorial(sector, inicio, fin))
        .collect(Collectors.toList());
  }

  private HCPorSectorTerritorial generarItemHCPorSectorTerritorial(SectorTerritorial sector, PeriodoMensual inicio, PeriodoMensual fin) {
    return new HCPorSectorTerritorial(sector.calcularHCEntre(inicio, fin), sector);
  }

  public List<EvolucionHCSectorTerritorial> reporteEvolucionHC(SectorTerritorial sector, PeriodoMensual inicio, PeriodoMensual fin) {

    GeneradorDePeriodos gen = new GeneradorDePeriodos();
    List<PeriodoMensual> periodos = gen.generarPeriodosMensualesEntre(inicio, fin);

    return periodos
        .stream()
        .map(periodo -> this.crearItemDelReporteDeEvolucionHC(sector, periodo))
        .collect(Collectors.toList());
  }

  private EvolucionHCSectorTerritorial crearItemDelReporteDeEvolucionHC(SectorTerritorial sector, PeriodoMensual periodo) {
    return new EvolucionHCSectorTerritorial(sector, periodo.getFecha(), sector.calcularHC(periodo));
  }
}
