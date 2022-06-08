package tipo.consumo;

import admin.FactorEmision;
import exceptions.UnidadFeNoCorrespondienteConUnidadTipoConsumo;

public abstract class TipoConsumo {
  String unidad;
  TipoActividad actividad;
  TipoAlcance alcance;
  FactorEmision factorEmision;

  public String unidadDeFactorEmisionPosible() {
    return unidad;
  }

  public void setFactorEmision(FactorEmision unFactorDeEmision) {
    String unidadFE = unFactorDeEmision.getUnidadDivisible();
    comprobarUnidadValida(unidadFE);
    this.factorEmision = unFactorDeEmision;
  }

  private void comprobarUnidadValida(String unidadDivisible) {
    if (!unidadDivisible.equals(unidadDeFactorEmisionPosible())) {
      throw new UnidadFeNoCorrespondienteConUnidadTipoConsumo(
          "La unidad del FE no se corresponde con la unidad del tipo.consumo.TipoConsumo");
    }
  }

  public TipoActividad getActividad() {
    return actividad;
  }

  public TipoAlcance getAlcance() {
    return alcance;
  }

  public FactorEmision getFactorEmision() {
    return factorEmision;
  }
}
