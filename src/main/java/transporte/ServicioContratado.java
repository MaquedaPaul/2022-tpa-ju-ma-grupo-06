package transporte;

import linea.PuntoUbicacion;
import services.geodds.ServicioGeodds;
import services.geodds.entities.Distancia;

import java.io.IOException;

public class ServicioContratado extends TransportePrivado {
  ServicioGeodds servicioGeodds = ServicioGeodds.getInstancia();
  private TipoVehiculo vehiculoContratado;

  public ServicioContratado(TipoVehiculo vehiculoContratado) {
    this.vehiculoContratado = vehiculoContratado;
  }

  public TipoVehiculo getVehiculoContratado() {
    return vehiculoContratado;
  }

}
