package linea;

import javax.persistence.*;

@Entity
public class Parada {

  @Id
  @GeneratedValue
  @Column(name = "ID_PARADA")
  Long id;

  @Column(name = "KM_ACTUAL")
  private int kmActual;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "ID_COORDENADA")
  private Coordenada coordenada;

  @Column(name = "ES_DE_IDA")
  private boolean esDeIda;

  public Parada() {
  }

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