package transporte;

import linea.PuntoUbicacion;
import services.geodds.ServicioGeodds;
import services.geodds.entities.Distancia;

import java.io.IOException;

public abstract class TransportePrivado extends Transporte {
  ServicioGeodds servicioGeodds = ServicioGeodds.getInstancia();

  @Override
  public double distanciaEntre(PuntoUbicacion origen, PuntoUbicacion destino) throws IOException {
    Distancia distancia = servicioGeodds.distancia(
        origen.getLocalidadId(),
        origen.getCalle(),
        origen.getAltura(),
        destino.getLocalidadId(),
        destino.getCalle(),
        destino.getAltura());
    return distancia.valor;
  }

  public void setServiocioGeo(ServicioGeodds unServicio) {
    this.servicioGeodds = unServicio;
  }

}
