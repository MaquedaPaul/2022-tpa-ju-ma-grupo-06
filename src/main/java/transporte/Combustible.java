package transporte;

import admin.FactorEmision;
import tipoconsumo.TipoConsumo;

public class Combustible {

  private final long consumoCombustiblePorMetro;
  private final TipoConsumo tipoConsumo;
  public Combustible(TipoConsumo tipoConsumo,
                     long consumoCombustiblePorMetro) {
    this.tipoConsumo = tipoConsumo;
    this.consumoCombustiblePorMetro = consumoCombustiblePorMetro;
  }

  public long calcularHc() {
    return (long) (this.consumoCombustiblePorMetro * this.getFactorEmision().getValor());
  }
  public TipoConsumo getTipoConsumo(){
    return this.tipoConsumo;
  }

  public FactorEmision getFactorEmision(){
    return getTipoConsumo().getFactorEmision();
  }
  public void setFactorEmision(FactorEmision unFactor){
    getTipoConsumo().setFactorEmision(unFactor);
  }
}
