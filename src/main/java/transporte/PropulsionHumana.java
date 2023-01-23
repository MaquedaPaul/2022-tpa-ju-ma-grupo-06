package transporte;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("PROPULSIÓN HUMANA")

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

}
