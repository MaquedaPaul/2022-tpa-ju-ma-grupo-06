package linea;

public class Parada {
  private int kmActual;
  private Coordenada coordenada;
  private boolean esDeIda;

  public Parada(int kmActual, Coordenada coordenada, boolean esDeIda) {
    this.kmActual = kmActual;
    this.coordenada = coordenada;
    this.esDeIda = esDeIda;
  }

  public int getKmActual() {
    return kmActual;
  }

  public Coordenada getCoordenada() {
    return coordenada;
  }

  public boolean esDeIda() {
    return esDeIda;
  }
}