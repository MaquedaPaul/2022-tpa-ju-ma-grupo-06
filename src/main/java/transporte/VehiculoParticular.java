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
  private String nombre;

  public VehiculoParticular() {
  }

  public VehiculoParticular(TipoVehiculo tipoVehiculo, double consumoPorKilometro, String nombre) {
    this.tipoVehiculo = tipoVehiculo;
    this.consumoPorKilometro = consumoPorKilometro;
    this.nombre = nombre;
  }

  @Override
  public TipoTransporte getTipoTransporte() {
    return TipoTransporte.VEHICULO_PARTICULAR;
  }

  @Override
  public String getDisplay() {
    return this.tipoVehiculo + " " + this.nombre;
  }
}
