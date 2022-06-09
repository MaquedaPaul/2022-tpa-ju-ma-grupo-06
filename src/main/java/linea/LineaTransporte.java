package linea;

import java.util.List;

public class LineaTransporte {
  private TipoTransporte tipoTransporte;
  private String nombre;
  private List<Parada> RecorridoDeIda;
  private List<Parada> recorridoVuelta;

  public LineaTransporte(TipoTransporte tipoTransporte, String nombre,
      List<Parada> RecorridoDeIda) {
    this.tipoTransporte = tipoTransporte;
    this.nombre = nombre;
    this.RecorridoDeIda = RecorridoDeIda;
  }

  public List<Parada> getParadas() {
    return RecorridoDeIda;
  }

  /**
   * Toma unaParada y la agrega en la posicionEnElRecorrido indicada.
   * 
   * @param unaParada
   * @param posicionEnElRecorrido
   */
  // ARREGLAR
  public void agregarParadaAlRecorrido(Parada unaParada, int posicionEnElRecorrido) {
    RecorridoDeIda.add(posicionEnElRecorrido - 1, unaParada);
  }

  public TipoTransporte transporte() {
    return tipoTransporte;
  }

  public Parada inicioDelRecorrido() {
    return RecorridoDeIda.get(0);
  }

  public Parada finalDelRecorrido() {
    return RecorridoDeIda.get(RecorridoDeIda.size() - 1);
  }

  public String getNombre() {
    return nombre;
  }
}
