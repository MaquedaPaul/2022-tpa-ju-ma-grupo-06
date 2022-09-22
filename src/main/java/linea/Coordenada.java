package linea;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

//TODO EMBEBER
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

  // hay que sobreescribir ambos

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Coordenada that = (Coordenada) o;
    return Double.compare(that.latitud, latitud) == 0 && Double.compare(that.longitud, longitud) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(latitud, longitud);
  }
}