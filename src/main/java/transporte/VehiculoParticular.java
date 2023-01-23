package transporte;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter@Setter
public class VehiculoParticular extends TransportePrivado {

  @Enumerated(EnumType.STRING)
  @Column(name = "TIPO_TRANSPORTE")
  private TipoVehiculo tipoVehiculo;

  protected VehiculoParticular() {
  }

  public VehiculoParticular(TipoVehiculo tipoVehiculo, double consumoPorKilometro, String nombre) {
    this.tipoVehiculo = tipoVehiculo;
    setConsumoPorKilometro(consumoPorKilometro);
    setNombre(nombre);
  }

  @Override
  public TipoTransporte getTipoTransporte() {
    return TipoTransporte.VEHICULO_PARTICULAR;
  }

}
