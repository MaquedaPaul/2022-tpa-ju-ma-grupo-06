package organizacion.periodo;

import java.time.LocalDate;

public class Mensual implements Periodo {

  private final LocalDate fecha;

  public Mensual(LocalDate fecha) {
    this.fecha = fecha;
  }

  public int getYear() {
    return fecha.getYear();
  }

  public int getMonthValue() {
    return fecha.getMonthValue();
  }

  public int perioricidad() {
    return 1;
  }
}
