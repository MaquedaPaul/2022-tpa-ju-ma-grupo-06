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

  @Embedded
  @AttributeOverrides( {
      @AttributeOverride(name="localidadId", column = @Column(name="origen_localidadId") ),
      @AttributeOverride(name="calle", column = @Column(name="origen_calle") ),
      @AttributeOverride(name="altura", column = @Column(name="origen_altura") )
  } )
  private PuntoUbicacion puntoOrigen;

  @Embedded
  @AttributeOverrides( {
      @AttributeOverride(name="localidadId", column = @Column(name="destino_localidadId") ),
      @AttributeOverride(name="calle", column = @Column(name="destino_calle") ),
      @AttributeOverride(name="altura", column = @Column(name="destino_altura") )
  } )
  private PuntoUbicacion puntoDestino;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "ID_TRANSPORTE")
  private Transporte transporteUtilizado;

  protected Tramo() {
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