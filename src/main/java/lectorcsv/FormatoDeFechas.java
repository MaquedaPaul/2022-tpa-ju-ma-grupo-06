package lectorcsv;

import java.util.HashMap;

public class FormatoDeFechas {

  private final HashMap<TipoPerioricidad, String> formatos;

  public FormatoDeFechas(HashMap<TipoPerioricidad, String> formatos) {
    this.formatos = formatos;
  }

  public boolean esUnPeriodoValido(TipoPerioricidad periodo) {
    return formatos.containsKey(periodo);
  }

  public boolean tieneElFormatoValido(String periodoImputacion, TipoPerioricidad perioricidad) {
    return periodoImputacion.matches(formatos.get(perioricidad));
  }

}
