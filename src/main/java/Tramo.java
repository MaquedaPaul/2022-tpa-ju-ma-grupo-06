import linea.Ubicacion;

public class Tramo {
  private final Ubicacion puntoOrigen;
  private final Ubicacion puntoDestino;
  private final Transporte transporteUtilizado;

  Tramo(Ubicacion puntoOrigen, Ubicacion puntoDestino, Transporte transporteUtilizado) {
    this.puntoOrigen = puntoOrigen;
    this.puntoDestino = puntoDestino;
    this.transporteUtilizado = transporteUtilizado;
  }

  public Ubicacion getPuntoOrigen() {
    return puntoOrigen;
  }

  public Ubicacion getPuntoDestino() {
    return puntoDestino;
  }

  public Transporte getTransporteUtilizado() {
    return transporteUtilizado;
  }
}
