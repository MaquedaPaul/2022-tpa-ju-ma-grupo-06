package transporte;

import exceptions.NoSePudoCalcularElTramo;
import java.io.IOException;
import linea.PuntoUbicacion;

public class Tramo {
  private final PuntoUbicacion puntoOrigen;
  private final PuntoUbicacion puntoDestino;
  private final Transporte transporteUtilizado;

  public Tramo(PuntoUbicacion puntoOrigen,
               PuntoUbicacion puntoDestino,
               Transporte transporteUtilizado) {
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

  public int distanciaTramo() {
    try {
      return this.transporteUtilizado.distanciaEntre(puntoOrigen, puntoDestino);
    } catch (IOException e) {
      e.printStackTrace();
    }
    throw new NoSePudoCalcularElTramo("API sin conexion");
  }
}