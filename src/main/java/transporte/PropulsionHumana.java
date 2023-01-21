package transporte;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
public class PropulsionHumana extends TransportePrivado {

  @Column(name = "TIPO_TRANSPORTE")
  private String herramientaUtilizada;

  protected PropulsionHumana() {
  }

  public PropulsionHumana(String herramientaUtilizada) {
    this.herramientaUtilizada = herramientaUtilizada;
    this.consumoPorKilometro = 0;
  }

  @Override
  public boolean sePuedeCompartir() {
    return false;
  }

  @Override
  public TipoTransporte getTipoTransporte() {
    return TipoTransporte.PROPULSION_HUMANA;
  }

  @Override
  public String getDisplay() {
    return this.herramientaUtilizada;
  }
}
