package mediciones;

import tipo.consumo.TipoConsumo;

public class Medicion {
  TipoConsumo tipoConsumo;
  Periodo periodicidad;
  double valor;
  String periodoDeImputacion;

  public Medicion(TipoConsumo unTipoConsumo,
                  Periodo unaPerioricidad,
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

  public Periodo getPeriodicidad() {
    return periodicidad;
  }

  public double getValor() {
    return valor;
  }

  public String getPeriodoDeImputacion() {
    return periodoDeImputacion;
  }


}

