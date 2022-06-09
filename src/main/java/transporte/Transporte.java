package transporte;

import java.io.IOException;
import linea.PuntoUbicacion;

public interface Transporte {
  public int distanciaEntre(PuntoUbicacion origen, PuntoUbicacion destino) throws IOException;
}


