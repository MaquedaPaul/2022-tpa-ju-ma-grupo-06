package mediciones;

import tipo.consumo.TipoConsumo;

public class Medicion {
  TipoConsumo tipoConsumo;
  Periodo perioricidad;
  double valor;
  String periodoDeImputacion;

  public Medicion(TipoConsumo unTipoConsumo, Periodo unaPerioricidad, double unValor, String periodoDeImputacion) {
    this.tipoConsumo = unTipoConsumo;
    this.perioricidad = unaPerioricidad;
    this.valor = unValor;
    this.periodoDeImputacion = periodoDeImputacion;
  }
}

