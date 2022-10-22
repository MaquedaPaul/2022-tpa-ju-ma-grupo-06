package lectorcsv;

public class Perioricidad {

  private final String periodoImputacion;
  private final TipoPerioricidad tipoPerioricidad;

  public Perioricidad(String periodoImputacion, TipoPerioricidad tipoPerioricidad) {
    this.tipoPerioricidad = tipoPerioricidad;
    this.periodoImputacion = periodoImputacion;
  }
}
