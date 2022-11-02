package reporte.itemsreportes;

import lombok.Getter;
import organizacion.Organizacion;

import java.time.LocalDate;

@Getter
public class EvolucionHCOrganizacion {

  private final Organizacion organizacion;
  private final LocalDate fecha;
  private final double valor;

  public EvolucionHCOrganizacion(Organizacion organizacion, LocalDate fecha, double valor) {
    this.organizacion = organizacion;
    this.fecha = fecha;
    this.valor = valor;
  }
}
