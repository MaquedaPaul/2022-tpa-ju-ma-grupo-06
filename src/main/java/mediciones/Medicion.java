package mediciones;

import java.time.LocalDate;

import tipo.consumo.TipoConsumo;

public class Medicion {
  TipoConsumo tipoConsumo;
  Perioricidad perioricidad;
  double valor;
  LocalDate fechaMedicion;

  public Medicion(TipoConsumo unTipoConsumo, Perioricidad unaPerioricidad, double unValor) {
    this.tipoConsumo = unTipoConsumo;
    this.perioricidad = unaPerioricidad;
    this.valor = unValor;
    fechaMedicion = LocalDate.now();
  }

  String obtenerPeriodoDeImputacion() {
    return perioricidad.calcularPeriodoImputacion(fechaMedicion);
  }
}

