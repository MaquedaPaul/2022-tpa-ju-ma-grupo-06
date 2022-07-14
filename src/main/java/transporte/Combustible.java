package transporte;

import global.Unidad;
import tipoconsumo.TipoActividad;
import tipoconsumo.TipoAlcance;
import tipoconsumo.TipoConsumo;

public class Combustible extends TipoConsumo {

  private final long consumoCombustiblePorMetro;

  public Combustible(String nombre, Unidad unidad, TipoActividad actividad, TipoAlcance alcance, long consumoCombustiblePorMetro) {
    super(nombre, unidad, actividad, alcance);
    this.consumoCombustiblePorMetro = consumoCombustiblePorMetro;
  }

  public long calcularHc() {
    return (long) (this.consumoCombustiblePorMetro*this.getFactorEmision().getValor());
  }
}
