package linea;

import javax.persistence.*;
import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.List;

@Entity
public class LineaTransporte {

  @Id
  @GeneratedValue
  @Column(name = "ID_LINEA_TRANSPORTE")
  Long id;

  @Enumerated(EnumType.STRING)
  private TipoTransporte tipoTransporte;
  private String nombre;



  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(name="PARADAS_POR_LINEA", joinColumns = @JoinColumn(name="ID_LINEA"), inverseJoinColumns = @JoinColumn(name="ID_PARADA"))
  private List<Parada> recorridoDeIda;

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(name="PARADAS_POR_LINEA", joinColumns = @JoinColumn(name="ID_LINEA"), inverseJoinColumns = @JoinColumn(name="ID_PARADA"))
  private List<Parada> recorridoVuelta;

  public LineaTransporte() {

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
