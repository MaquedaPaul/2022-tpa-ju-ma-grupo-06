package admin;

import global.Unidad;

import javax.persistence.*;

@Entity
public class FactorEmision {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "valor")
  private double valor;

  @Enumerated
  private Unidad unidadDivisible;

  public FactorEmision() {
  }

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
