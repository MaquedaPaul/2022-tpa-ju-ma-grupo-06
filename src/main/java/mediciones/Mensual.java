package mediciones;

import java.time.LocalDate;

public class Mensual implements Perioricidad {
  public String calcularPeriodoImputacion(LocalDate unaFecha) {
    return obtenerMes(unaFecha) + obtenerAnio(unaFecha);
  }

  public String obtenerMes(LocalDate unaFecha) {
    return Integer.toString(unaFecha.getMonthValue());
  }

  public String obtenerAnio(LocalDate unaFecha) {
    return Integer.toString(unaFecha.getYear());
  }
}

