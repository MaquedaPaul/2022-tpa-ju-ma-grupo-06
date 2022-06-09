package transporte;

import linea.PuntoUbicacion;
import services.geodds.ServicioGeodds;
import services.geodds.entities.Distancia;

import java.io.IOException;

public class PropulsionHumana implements Transporte {
  ServicioGeodds servicioGeodds = ServicioGeodds.getInstancia();
  private String herramientaUtilizada;

  public PropulsionHumana(String herramientaUtilizada) {
    this.herramientaUtilizada = herramientaUtilizada;
  }

  public String getHerramientaUtilizada() {
    return herramientaUtilizada;
  }

  @Override
  public int distanciaEntre(PuntoUbicacion origen, PuntoUbicacion destino) throws IOException {

    ServicioGeodds servicioGeodds = ServicioGeodds.getInstancia();
    Distancia distancia = servicioGeodds.distancia(origen.getLocalidadId(), origen.getCalle(), origen.getAltura(), destino.getLocalidadId(), destino.getCalle(), destino.getAltura());

    return distancia.valor;
  }
}
