package transporte;

import admin.FactorEmision;
import tipoconsumo.TipoConsumo;

import javax.persistence.*;

@Entity
public class Combustible {

  @Id
  @GeneratedValue
  @Column(name = "ID_COMBUSTIBLE")
  Long id;

  @Transient
  private TipoConsumo tipoConsumo;

  public Combustible() {
  }

  public Combustible(TipoConsumo tipoConsumo) {
    this.tipoConsumo = tipoConsumo;
  }

  public TipoConsumo getTipoConsumo() {
    return this.tipoConsumo;
  }

  public double obtenerValorEmision() {
    return tipoConsumo.getFactorEmision().getValor();
  }

  public FactorEmision getFactorEmision() {
    return getTipoConsumo().getFactorEmision();
  }
}
