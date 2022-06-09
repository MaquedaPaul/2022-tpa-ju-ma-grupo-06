package linea;

import java.util.ArrayList;
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
   * @param sentido
   */
  // ARREGLAR
  public void agregarParadaAlRecorrido(Parada unaParada) {
    if (unaParada.esDeIda()) {
      recorridoDeIda.add(unaParada);
    } else {
      recorridoVuelta.add(unaParada);
    }
  }

  public TipoTransporte transporte() {
    return tipoTransporte;
  }

  public Parada inicioDelRecorridoDeIda() {
    return recorridoDeIda.get(0);
  }

  public Parada finalDelRecorridoDeIda() {
    return recorridoDeIda.get(recorridoDeIda.size() - 1);
  }

  public Parada inicioDelRecorridoDeRegreso() {
    return recorridoDeIda.get(0);
  }

  public Parada finalDelRecorridoDeRegreso() {
    return recorridoDeIda.get(recorridoDeIda.size() - 1);
  }

  public String getNombre() {
    return nombre;
  }
}
