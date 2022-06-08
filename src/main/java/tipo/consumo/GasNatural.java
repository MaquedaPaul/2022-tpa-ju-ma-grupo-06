package tipo.consumo;

public class GasNatural extends TipoConsumo {

  public GasNatural() {
    unidad = "m3";
    actividad = TipoActividad.COMBUSTION_FIJA;
    alcance = TipoAlcance.EMISION_DIRECTA;
  }
}
