package admin;

public class FactorEmision {
  double valor;
  String unidadDivisible;

  public FactorEmision(double unValor, String unidadDivisible) {
    valor = unValor;
    this.unidadDivisible = unidadDivisible;
  }

  public double getValor() {
    return valor;
  }

  public String getUnidadDivisible() {
    return unidadDivisible;
  }

  public void setValor(double valor) {
    this.valor = valor;
  }
}
