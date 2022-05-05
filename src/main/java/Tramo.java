import Linea.Parada;

public class Tramo {
  private final Parada puntoOrigen;
  private final Parada puntoDestino;
  private final Transporte transporteUtilizado;

  Tramo(Parada puntoOrigen, Parada puntoDestino, Transporte transporteUtilizado) {
    this.puntoOrigen = puntoOrigen;
    this.puntoDestino = puntoDestino;
    this.transporteUtilizado = transporteUtilizado;
  }

  public Parada getPuntoOrigen() {
    return puntoOrigen;
  }

  public Parada getPuntoDestino() {
    return puntoDestino;
  }

  public Transporte getTransporteUtilizado() {
    return transporteUtilizado;
  }
}
