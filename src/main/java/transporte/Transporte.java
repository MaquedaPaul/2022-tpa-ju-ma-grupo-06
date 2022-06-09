package transporte;

import linea.PuntoUbicacion;

import java.io.IOException;

public interface Transporte {
  public int distanciaEntre(PuntoUbicacion origen, PuntoUbicacion destino) throws IOException;
}


