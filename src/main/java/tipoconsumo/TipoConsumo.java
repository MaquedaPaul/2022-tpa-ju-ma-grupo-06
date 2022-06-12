package tipoconsumo;

import admin.FactorEmision;
import exceptions.UnidadFeNoCorrespondienteConUnidadTipoConsumo;

public class TipoConsumo {

  private final String nombre;
  private final String unidad;
  private final TipoActividad actividad;
  private final TipoAlcance alcance;
  private FactorEmision factorEmision;

  public TipoConsumo(String nombre, String unidad, TipoActividad actividad, TipoAlcance alcance) {
    this.nombre = nombre;
    this.unidad = unidad;
    this.actividad = actividad;
    this.alcance = alcance;
  }

  public String unidadDeFactorEmisionPosible() {
    return unidad;
  }

  public void setFactorEmision(FactorEmision unFactorDeEmision) {
    String unidadFE = unFactorDeEmision.getUnidadDivisible();
    this.comprobarUnidadValida(unidadFE);
    this.factorEmision = unFactorDeEmision;
  }

  private void comprobarUnidadValida(String unidadDivisible) {
    if (!unidadDivisible.equals(this.unidadDeFactorEmisionPosible())) {
      throw new UnidadFeNoCorrespondienteConUnidadTipoConsumo(
          "La unidad del FE no se corresponde con la unidad del tipo.consumo.TipoConsumo");
    }
  }

  public String getNombre() {
    return nombre;
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
