package reporte.itemsreportes;

import lombok.Getter;
import territorio.SectorTerritorial;

import java.time.LocalDate;

@Getter
public class EvolucionHCSectorTerritorial {

  private final SectorTerritorial sector;
  private final LocalDate fecha;
  private final double valor;

  public EvolucionHCSectorTerritorial(SectorTerritorial sector, LocalDate fecha, double valor) {
    this.sector = sector;
    this.fecha = fecha;
    this.valor = valor;
  }
}
