package transporte;


import java.io.IOException;
import linea.PuntoUbicacion;
import services.geodds.ServicioGeodds;
import services.geodds.entities.Distancia;

public class ServicioContratado implements Transporte {
  ServicioGeodds servicioGeodds = ServicioGeodds.getInstancia();
  private TipoVehiculo vehiculoContratado;

  public ServicioContratado(TipoVehiculo vehiculoContratado) {
    this.vehiculoContratado = vehiculoContratado;
  }

  public TipoVehiculo getVehiculoContratado() {
    return vehiculoContratado;
  }

  @Override
  public double distanciaEntre(PuntoUbicacion origen, PuntoUbicacion destino) throws IOException {
    Distancia distancia = servicioGeodds
        .distancia(
            origen.getLocalidadId(),
            origen.getCalle(),
            origen.getAltura(),
            destino.getLocalidadId(),
            destino.getCalle(),
            destino.getAltura());
    return distancia.valor;
  }
}
