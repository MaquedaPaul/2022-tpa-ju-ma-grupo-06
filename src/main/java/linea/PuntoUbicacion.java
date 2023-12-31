package linea;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Embeddable @Getter @Setter
public class PuntoUbicacion {

  @Column(name = "ID_LOCALIDAD")
  private int localidadId;

  @Column(name = "CALLE")
  private String calle;

  @Column(name = "ALTURA")
  private int altura;

  protected PuntoUbicacion() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PuntoUbicacion)) return false;
    PuntoUbicacion that = (PuntoUbicacion) o;
    return getLocalidadId() == that.getLocalidadId() && getAltura() == that.getAltura() && Objects.equals(getCalle(), that.getCalle());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getLocalidadId(), getCalle(), getAltura());
  }

  public PuntoUbicacion(int localidadId, String calle, int altura) {
    this.localidadId = localidadId;
    this.calle = calle.toUpperCase();
    this.altura = altura;
  }

  public boolean esEnLaDireccionDe(PuntoUbicacion puntoUbicacion) {
    return this.getCalle().equals(puntoUbicacion.getCalle()) && this.getAltura() == puntoUbicacion.getAltura();
  }
}
