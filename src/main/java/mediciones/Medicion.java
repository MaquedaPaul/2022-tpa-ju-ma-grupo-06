package mediciones;

import organizacion.Organizacion;
import tipoconsumo.TipoConsumo;

public class Medicion {
  TipoConsumo tipoConsumo;
  Perioricidad periodicidad;
  double valor;
  String periodoDeImputacion;
  final Organizacion organizacion;

  public Medicion(TipoConsumo unTipoConsumo,
                  Perioricidad unaPerioricidad,
                  double unValor,
                  String periodoDeImputacion,Organizacion organizacion) {
    this.tipoConsumo = unTipoConsumo;
    this.periodicidad = unaPerioricidad;
    this.valor = unValor;
    this.periodoDeImputacion = periodoDeImputacion;
    this.organizacion =  organizacion;
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

  public boolean perteneceA(Organizacion organizacion) {
    return this.organizacion == organizacion;
  }
}

