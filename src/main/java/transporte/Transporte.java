package transporte;

import linea.PuntoUbicacion;
import services.geodds.ServicioGeodds;

import java.io.IOException;

public interface Transporte {
  public double distanciaEntre(PuntoUbicacion origen, PuntoUbicacion destino) throws IOException;
}


