package linea;

import java.util.Objects;

public class BuilderPuntoUbicacion {
  private Integer localidadId;
  private String calle;
  private Integer altura;

  public BuilderPuntoUbicacion setLocalidadId(String localidadId) {
    this.localidadId = Integer.parseInt(localidadId);
    return this;
  }

  public BuilderPuntoUbicacion setCalle(String calle) {
    this.calle = calle;
    return this;
  }

  public BuilderPuntoUbicacion setAltura(String altura) {
    this.altura = Integer.parseInt(altura);
    return this;
  }

  public PuntoUbicacion build() {
    Objects.requireNonNull(this.localidadId);
    Objects.requireNonNull(this.calle);
    Objects.requireNonNull(this.altura);
    return new PuntoUbicacion(this.localidadId, this.calle, this.altura);
  }

}
