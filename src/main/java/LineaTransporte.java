import java.util.List;

public abstract class LineaTransporte {
  private final TipoLinea tipoLinea;
  private String nombre;

  private final List<Parada> paradas;
  LineaTransporte(TipoLinea tipoLinea, String nombre, List<Parada> paradas) {
    this.tipoLinea = tipoLinea;
    this.nombre = nombre;
    this.paradas = paradas;
  }

  public List<Parada> getParadas() {
    return paradas;
  }

  public void agregarParadaAlRecorrido(Parada unaParada, int posicionEnElRecorrido) {
    paradas.add(posicionEnElRecorrido-1,unaParada);
  }
}
