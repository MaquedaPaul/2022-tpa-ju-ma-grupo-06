package transporte;

import linea.PuntoUbicacion;
import services.geodds.ServicioGeodds;
import services.geodds.entities.Distancia;

import java.io.IOException;

public class VehiculoParticular extends TransportePrivado {
  public ServicioGeodds servicioGeodds = ServicioGeodds.getInstancia();
  private TipoVehiculo tipoVehiculo;
  private TipoCombustible tipoCombustible;

  public VehiculoParticular(TipoCombustible tipoCombustible, TipoVehiculo tipoVehiculo) {
    this.tipoCombustible = tipoCombustible;
    this.tipoVehiculo = tipoVehiculo;
  }

  public TipoVehiculo getTipoVehiculo() {
    return tipoVehiculo;
  }

  public TipoCombustible getTipoCombustible() {
    return tipoCombustible;
  }


}
