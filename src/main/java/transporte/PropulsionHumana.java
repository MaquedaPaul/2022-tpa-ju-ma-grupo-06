package transporte;

import java.io.IOException;
import linea.PuntoUbicacion;
import services.geodds.ServicioGeodds;
import services.geodds.entities.Distancia;

public class PropulsionHumana implements Transporte {
  ServicioGeodds servicioGeodds = ServicioGeodds.getInstancia();
  private String herramientaUtilizada;

  public PropulsionHumana(String herramientaUtilizada) {
    this.herramientaUtilizada = herramientaUtilizada;
  }


  @Override
  public int distanciaEntre(PuntoUbicacion origen, PuntoUbicacion destino) throws IOException {

    ServicioGeodds servicioGeodds = ServicioGeodds.getInstancia();
    Distancia distancia = servicioGeodds
        .distancia(origen.getLocalidadId(),
            origen.getCalle(),
            origen.getAltura(),
            destino.getLocalidadId(),
            destino.getCalle(),
            destino.getAltura());
    return distancia.valor;
  }

  public String getHerramientaUtilizada() {
    return herramientaUtilizada;
  }

  public ServicioGeodds getApiGeodds() {
    return servicioGeodds;
  }

}
