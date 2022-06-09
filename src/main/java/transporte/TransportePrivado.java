package transporte;

import linea.PuntoUbicacion;
import services.geodds.ServicioGeodds;
import services.geodds.entities.Distancia;

import java.io.IOException;

public abstract class TransportePrivado implements Transporte{
  @Override
  public double distanciaEntre(PuntoUbicacion origen, PuntoUbicacion destino) throws IOException {

    ServicioGeodds servicioGeodds = ServicioGeodds.getInstancia();
    Distancia distancia = servicioGeodds.distancia(origen.getLocalidadId(), origen.getCalle(), origen.getAltura(), destino.getLocalidadId(), destino.getCalle(), destino.getAltura());

    return distancia.valor;
  }
}
