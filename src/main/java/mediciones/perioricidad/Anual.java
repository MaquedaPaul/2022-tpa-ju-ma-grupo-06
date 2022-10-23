package mediciones.perioricidad;

import lombok.Getter;
import organizacion.periodo.Periodo;

import java.time.LocalDate;

@Getter
public class Anual implements Perioricidad {

  private final LocalDate fechaImputacion;

  public Anual(LocalDate fechaImputacion) {
    this.fechaImputacion = fechaImputacion;
  }

  public boolean esDelPeriodo(Periodo periodo) {
    return fechaImputacion.getYear() == periodo.getYear();
  }
}
