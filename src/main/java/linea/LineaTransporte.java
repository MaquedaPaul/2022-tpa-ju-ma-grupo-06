package linea;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
public class LineaTransporte {

  @Id
  @GeneratedValue
  @Column(name = "ID_LINEA_TRANSPORTE")
  Long id;

  @Enumerated(EnumType.STRING)
  private TipoTransporte tipoTransporte;
  private String nombre;

  @ElementCollection
  @OrderBy(value = "kmActual")
  private List<Parada> recorridoDeIda;

  @ElementCollection
  @OrderBy(value = "kmActual")
  private List<Parada> recorridoVuelta;

  protected LineaTransporte() {

  }

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

  public void agregarParadaAlRecorrido(Parada unaParada, boolean esDeIda) {
    if (esDeIda) {
      recorridoDeIda.add(unaParada);
    } else {
      recorridoVuelta.add(unaParada);
    }
  }

  public TipoTransporte transporte() {
    return tipoTransporte;
  }

  //Parada: primera = km 0
  //PARADA: ultima = km mas alto
  public Parada inicioDelRecorridoDeIda() {
    return recorridoDeIda.get(0);
  }

  public Parada finalDelRecorridoDeIda() {
    return recorridoDeIda.get(recorridoDeIda.size() - 1);
  }

  public Parada inicioDelRecorridoDeRegreso() {
    return recorridoVuelta.get(0);
  }

  public Parada finalDelRecorridoDeRegreso() {
    return recorridoVuelta.get(recorridoVuelta.size() - 1);
  }

  public boolean existeParadaEnElKm(int km, String sentido) {
    switch (sentido) {
      case "IDA": return this.recorridoDeIda.stream().anyMatch(p -> p.getKmActual() == km);
      case "VUELTA": return this.recorridoVuelta.stream().anyMatch(p -> p.getKmActual() == km);
      default: return false;
    }
  }


}
