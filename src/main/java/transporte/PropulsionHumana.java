package transporte;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
public class PropulsionHumana extends TransportePrivado {

  public PropulsionHumana() {
  }

  public PropulsionHumana(String herramientaUtilizada) {
    setNombre(herramientaUtilizada);
    setConsumoPorKilometro(0);
  }

  @Override
  public boolean sePuedeCompartir() {
    return false;
  }

  @Override
  public TipoTransporte getTipoTransporte() {
    return TipoTransporte.PROPULSION_HUMANA;
  }

}
