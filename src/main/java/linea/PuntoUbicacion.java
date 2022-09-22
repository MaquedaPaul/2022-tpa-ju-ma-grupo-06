package linea;

import javax.persistence.*;

@Entity
public class PuntoUbicacion {

  @Id
  @GeneratedValue
  @Column(name = "ID_PUNTO_UBICACION")
  Long id;

  @Column(name = "ID_LOCALIDAD")
  private int localidadId;

  @Column(name = "CALLE")
  private String calle;

  @Column(name = "ALTURA")
  private int altura;

  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "ID_COODERNADA")
  private Coordenada coordenada;


  public PuntoUbicacion() {
  }

  public PuntoUbicacion(int localidadId, String calle, int altura, Coordenada coordenada) {
    this.localidadId = localidadId;
    this.calle = calle;
    this.altura = altura;
    this.coordenada = coordenada;
  }

  public int getLocalidadId() {
    return localidadId;
  }

  public String getCalle() {
    return calle;
  }

  public int getAltura() {
    return altura;
  }

  public Coordenada getCoordenada() {
    return coordenada;
  }
}
