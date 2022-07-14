package transporte;

//import java.io.IOException;
//import linea.PuntoUbicacion;
//import services.geodds.ServicioGeodds;
//import services.geodds.entities.Distancia;

public class ServicioContratado extends TransportePrivado {

  private TipoVehiculo vehiculoContratado;

  public ServicioContratado(TipoVehiculo vehiculoContratado) {
    this.vehiculoContratado = vehiculoContratado;
  }

  public TipoVehiculo getVehiculoContratado() {
    return vehiculoContratado;
  }

}
