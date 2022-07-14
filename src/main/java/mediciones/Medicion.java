package mediciones;

import tipoconsumo.TipoConsumo;

public class Medicion {
  TipoConsumo tipoConsumo;
  Perioricidad periodicidad;
  double valor;
  String periodoDeImputacion;

  public Medicion(TipoConsumo unTipoConsumo,
                  Perioricidad unaPerioricidad,
                  double unValor,
                  String periodoDeImputacion) {
    this.tipoConsumo = unTipoConsumo;
    this.periodicidad = unaPerioricidad;
    this.valor = unValor;
    this.periodoDeImputacion = periodoDeImputacion;
  }

  public TipoConsumo getTipoConsumo() {
    return tipoConsumo;
  }

  public Perioricidad getPeriodicidad() {
    return periodicidad;
  }

  public double getValor() {
    return valor;
  }

  public String getPeriodoDeImputacion() {
    return periodoDeImputacion;
  }

  public double calcularHc() {
    return getValor() + tipoConsumo.getFactorEmision().getValor();
  }


}

