package mediciones;

import java.time.LocalDate;

public interface Perioricidad {
  public String calcularPeriodoImputacion(LocalDate unaFecha);
}

