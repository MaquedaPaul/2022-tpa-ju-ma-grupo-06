package admin;

import global.Unidad;

public class FactorEmision {
  double valor;
  Unidad unidadDivisible;

  public FactorEmision(double unValor, Unidad unidadDivisible) {
    valor = unValor;
    this.unidadDivisible = unidadDivisible;
  }

  public double getValor() {
    return valor;
  }

  public Unidad getUnidadDivisible() {
    return unidadDivisible;
  }

  public void setValor(double valor) {
    this.valor = valor;
  }
}
