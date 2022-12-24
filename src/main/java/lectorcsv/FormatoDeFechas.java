package lectorcsv;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;


public class FormatoDeFechas {

  private final HashMap<TipoPerioricidad, DateTimeFormatter> formatos;

  public FormatoDeFechas(HashMap<TipoPerioricidad, DateTimeFormatter> formatos) {
    this.formatos = formatos;
  }

  public boolean tieneElFormatoValido(String periodoImputacion, TipoPerioricidad perioricidad) {
    return this.esUnPeriodoValido(perioricidad) && this.puedoParsear(perioricidad, periodoImputacion);
  }

  public LocalDate parsear(String fecha, TipoPerioricidad tipo) {
    return LocalDate.from(formatos.get(tipo).parse(fecha));
  }

  private boolean puedoParsear(TipoPerioricidad perioricidad, String periodoImputacion) {

    try {
      formatos.get(perioricidad).parse(periodoImputacion);
    } catch (DateTimeParseException ignored) {
      return false;
    }
    return true;
  }

  private boolean esUnPeriodoValido(TipoPerioricidad periodo) {
    return formatos.containsKey(periodo);
  }

}
