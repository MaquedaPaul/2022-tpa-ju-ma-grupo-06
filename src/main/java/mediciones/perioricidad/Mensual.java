package mediciones.perioricidad;

import organizacion.periodo.Periodo;

import java.time.LocalDate;

public class Mensual implements Perioricidad {

  private final LocalDate fecha;

  public Mensual(LocalDate fecha) {
    this.fecha = fecha;
  }

  public boolean esDelPeriodo(Periodo periodo) {
    return fecha.getYear() == periodo.getYear() && fecha.getMonthValue() == periodo.getMonthValue();
  }
}
