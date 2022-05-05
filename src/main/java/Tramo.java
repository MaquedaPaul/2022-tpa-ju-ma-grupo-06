public class Tramo {
  private final String puntoOrigen;
  private final String puntoDestino;
  private final Transporte transporteUtilizado;

  Tramo(String puntoOrigen, String puntoDestino, Transporte transporteUtilizado) {
    this.puntoOrigen = puntoOrigen;
    this.puntoDestino = puntoDestino;
    this.transporteUtilizado = transporteUtilizado;
  }

  public String getPuntoOrigen() {
    return puntoOrigen;
  }

  public String getPuntoDestino() {
    return puntoDestino;
  }

  public Transporte getTransporteUtilizado() {
    return transporteUtilizado;
  }
}
