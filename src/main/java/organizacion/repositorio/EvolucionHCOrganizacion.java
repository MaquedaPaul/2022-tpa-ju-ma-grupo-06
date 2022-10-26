package organizacion.repositorio;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EvolucionHCOrganizacion {

  private final LocalDate fecha;
  private final double valor;

  public EvolucionHCOrganizacion(LocalDate fecha, double valor) {
    this.fecha = fecha;
    this.valor = valor;
  }
}
