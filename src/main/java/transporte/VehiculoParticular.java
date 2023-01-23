package transporte;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
@DiscriminatorValue("VEHICULO PARTICULAR")
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

}
