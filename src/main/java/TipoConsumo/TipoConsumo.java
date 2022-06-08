package TipoConsumo;

import exceptions.UnidadFENoCorrespondienteConUnidadTipoConsumo;
import admin.FactorEmision;
public abstract class TipoConsumo {
  String unidad;
  TipoActividad actividad;
  TipoAlcance alcance;
  FactorEmision factorEmision;
  public String unidadDeFactorEmisionPosible(){
    return unidad;
  }

  public void setFactorEmision(FactorEmision unFactorDeEmision){
    String unidadFE = unFactorDeEmision.getUnidadDivisible();
    comprobarUnidadValida(unidadFE);
    this.factorEmision = unFactorDeEmision;
  }
  private void comprobarUnidadValida(String unidadDivisible){
    if(!unidadDivisible.equals(unidadDeFactorEmisionPosible())){
      throw new UnidadFENoCorrespondienteConUnidadTipoConsumo("La unidad del FE no se corresponde con la unidad del TipoConsumo.TipoConsumo");
    }
  }
}
