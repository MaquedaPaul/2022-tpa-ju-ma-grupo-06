package transporte;

import exceptions.NoSePudoCalcularElTramo;
import linea.PuntoUbicacion;
import lombok.Getter;
import lombok.Setter;
import tipoconsumo.TipoConsumo;

import javax.persistence.*;
import java.io.IOException;

@Embeddable
@Getter
@Setter
public class Tramo {
  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "ID_PUNTO_ORIGEN")
  private PuntoUbicacion puntoOrigen;

  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "ID_PUNTO_DESTINO")
  private PuntoUbicacion puntoDestino;

  @ManyToOne
  @JoinColumn(name = "ID_TRANSPORTE")
  private Transporte transporteUtilizado;

  public Tramo() {
  }

  public Tramo(PuntoUbicacion puntoOrigen,
               PuntoUbicacion puntoDestino,
               Transporte transporteUtilizado) {
    this.puntoOrigen = puntoOrigen;
    this.puntoDestino = puntoDestino;
    this.transporteUtilizado = transporteUtilizado;
  }

  public double distanciaTramo() {
    try {
      return this.transporteUtilizado.distanciaEntre(puntoOrigen, puntoDestino);
    } catch (IOException e) {
      e.printStackTrace();
    }
    throw new NoSePudoCalcularElTramo("API sin conexion");
  }

  public boolean sePuedeCompartir() {
    return this.getTransporteUtilizado().sePuedeCompartir();
  }

  public double calcularHc() {
    return transporteUtilizado.calcularHc() * distanciaTramo();
  }

  public TipoConsumo getTipoConsumo() {
    return this.transporteUtilizado.getTipoConsumo();
  }

}