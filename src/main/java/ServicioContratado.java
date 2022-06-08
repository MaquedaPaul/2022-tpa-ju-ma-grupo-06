import linea.PuntoUbicacion;
import services.geodds.ServicioGeodds;
import services.geodds.entities.Distancia;

import java.io.IOException;

public class ServicioContratado implements Transporte {
  private TipoVehiculo vehiculoContratado;

  public ServicioContratado(TipoVehiculo vehiculoContratado) {
    this.vehiculoContratado = vehiculoContratado;
  }

  public TipoVehiculo getVehiculoContratado() {
    return vehiculoContratado;
  }

  @Override
  public int distanciaEntre(PuntoUbicacion origen, PuntoUbicacion destino) throws IOException {

    ServicioGeodds servicioGeodds = ServicioGeodds.getInstancia();
    Distancia distancia = servicioGeodds.distancia(origen.getLocalidadId(), origen.getCalle(), origen.getAltura(), destino.getLocalidadId(), destino.getCalle(), destino.getAltura());

    return distancia.valor;
  }
}
