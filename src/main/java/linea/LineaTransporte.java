package linea;

import java.util.ArrayList;
import java.util.List;

public class LineaTransporte {
  private final TipoTransporte tipoTransporte;
  private final String nombre;
  private final List<Parada> recorridoDeIda;
  private final List<Parada> recorridoVuelta;

  public LineaTransporte(TipoTransporte tipoTransporte, String nombre,
                         List<Parada> recorridoDeIda, List<Parada> recorridoVuelta) {
    this.tipoTransporte = tipoTransporte;
    this.nombre = nombre;
    this.recorridoDeIda = recorridoDeIda;
    this.recorridoVuelta = recorridoVuelta;
  }

  public List<Parada> getRecorridoDeIda() {
    return recorridoDeIda;
  }

  public List<Parada> getRecorridoVuelta() {
    return recorridoVuelta;
  }

  public List<Parada> getRecorridoTotal() {
    List<Parada> paradas = new ArrayList<>();
    paradas.addAll(this.getRecorridoDeIda());
    paradas.addAll(this.getRecorridoVuelta());
    return paradas;
  }

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
