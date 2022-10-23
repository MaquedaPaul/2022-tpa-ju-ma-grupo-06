package organizacion.periodo;

import java.time.LocalDate;

public class Anual implements Periodo {

  private final LocalDate fecha;

  public Anual(LocalDate fecha) {
    this.fecha = fecha;
  }

  public int getYear() {
    return fecha.getYear();
  }

  public int getMonthValue() {
    return fecha.getMonthValue();
  }

  public int perioricidad() {
    return 12;
  }
}
