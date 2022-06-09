package mediciones;

import java.time.LocalDate;

public class Anual implements Perioricidad {

  public String calcularPeriodoImputacion(LocalDate unaFecha) {
    return Integer.toString(unaFecha.getYear());
  }
}

