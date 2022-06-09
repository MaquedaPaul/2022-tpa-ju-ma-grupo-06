import linea.PuntoUbicacion;
import linea.PuntoUbicacionTransportePublico;

import java.io.IOException;

public class Tramo {
  private final PuntoUbicacion puntoOrigen;
  private final PuntoUbicacion puntoDestino;
  private final Transporte transporteUtilizado;

  Tramo(PuntoUbicacion puntoOrigen, PuntoUbicacion puntoDestino, Transporte transporteUtilizado) {
    this.puntoOrigen = puntoOrigen;
    this.puntoDestino = puntoDestino;
    this.transporteUtilizado = transporteUtilizado;
  }

  public PuntoUbicacion getPuntoOrigen() {
    return puntoOrigen;
  }

  public PuntoUbicacion getPuntoDestino() {
    return puntoDestino;
  }

  public Transporte getTransporteUtilizado() {
    return transporteUtilizado;
  }

  public int distanciaTramo() throws IOException {
    return this.transporteUtilizado.distanciaEntre(puntoOrigen,puntoDestino);
  }
}
