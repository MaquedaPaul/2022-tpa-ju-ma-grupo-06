package linea;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Coordenada {

  @Id
  @GeneratedValue
  @Column(name = "ID_COORDENADA")
  Long id;

  @Column(name = "LATITUD")
  private double latitud;

  @Column(name = "LONGITUD")
  private double longitud;

  public Coordenada() {
  }

  public Coordenada(double latitud, double longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }

  public double getLatitud() {
    return latitud;
  }

  public double getLongitud() {
    return longitud;
  }
}