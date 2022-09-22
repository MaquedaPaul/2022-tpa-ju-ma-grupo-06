package transporte;

//import java.io.IOException;
//import linea.PuntoUbicacion;
//import services.geodds.ServicioGeodds;
//import services.geodds.entities.Distancia;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class VehiculoParticular extends TransportePrivado {

  @Enumerated(EnumType.STRING)
  @Column(name = "TIPO_TRANSPORTE")
  private TipoVehiculo tipoVehiculo;

  public VehiculoParticular() {
  }

  public VehiculoParticular(TipoVehiculo tipoVehiculo, double consumoPorKilometro) {
    this.tipoVehiculo = tipoVehiculo;
    this.consumoPorKilometro = consumoPorKilometro;

  }

  public TipoVehiculo getTipoVehiculo() {
    return tipoVehiculo;
  }

  public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
    this.tipoVehiculo = tipoVehiculo;
  }
}
