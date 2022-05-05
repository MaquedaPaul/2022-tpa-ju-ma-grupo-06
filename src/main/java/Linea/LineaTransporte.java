package Linea;
import java.util.List;

public class LineaTransporte {
  private TipoTransporte tipoTransporte;
  private String nombre;

  private final List<Parada> paradas;
  public LineaTransporte(TipoTransporte tipoTransporte, String nombre, List<Parada> paradas) {
    this.tipoTransporte = tipoTransporte;
    this.nombre = nombre;
    this.paradas = paradas;
  }

  public List<Parada> getParadas() {
    return paradas;
  }

  public void agregarParadaAlRecorrido(Parada unaParada, int posicionEnElRecorrido) {
    paradas.add(posicionEnElRecorrido-1,unaParada);
  }

  public TipoTransporte transporte() {
    return tipoTransporte;
  }

  public Parada inicioDelRecorrido() {
    return paradas.get(0);
  }

  public Parada finalDelRecorrido() {
    return paradas.get(paradas.size()-1);
  }
}
