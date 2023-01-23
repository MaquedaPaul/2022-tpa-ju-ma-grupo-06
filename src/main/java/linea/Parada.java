package linea;

import lombok.Getter;

import javax.persistence.*;

@Embeddable @Getter
public class Parada {

  @Column(name = "KM_ACTUAL")
  private int kmActual;

  @Embedded
  private PuntoUbicacion puntoUbicacion;

  protected Parada() {
  }

  public Parada(int kmActual,PuntoUbicacion puntoUbicacion) {
    this.kmActual = kmActual;
    this.puntoUbicacion = puntoUbicacion;
  }

  public PuntoUbicacion getPuntoUbicacion(){
    return this.puntoUbicacion;
  }

}