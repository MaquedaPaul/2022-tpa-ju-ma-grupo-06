import exceptions.UnidadFENoCorrespondienteConUnidadTIpoConsumo;

public abstract class TipoConsumo {
  String unidad;
  TipoActividad actividad;
  TipoAlcance alcance;
  //FactorEmision factorEmision;
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
      throw new UnidadFENoCorrespondienteConUnidadTIpoConsumo("La unidad del FE no se corresponde con la unidad del TipoConsumo");
    }
  }
}
