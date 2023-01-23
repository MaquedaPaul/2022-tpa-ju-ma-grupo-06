package transporte;

import linea.PuntoUbicacion;
import lombok.Getter;
import lombok.Setter;
import tipoconsumo.TipoConsumo;

import javax.persistence.*;
import java.io.IOException;


@Entity
@Getter@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TRANSPORTE_UTILIZADO")
public abstract class Transporte {

  @Id
  @GeneratedValue
  @Column(name = "ID_TRANSPORTE")
  private Long id;

  @Column(name = "CONSUMO_POR_KM")
  private double consumoPorKilometro;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "ID_COMBUSTIBLE")
  private Combustible combustible;

  private String nombre;
  public boolean sePuedeCompartir() {
    return false;
  }

  public abstract double distanciaEntre(PuntoUbicacion origen,
                                        PuntoUbicacion destino) throws IOException;

  public double calcularHc() {
    return combustible.obtenerValorEmision() * this.consumoPorKilometro;
  }

  public TipoConsumo getTipoConsumo() {
    return combustible.getTipoConsumo();
  }

  public abstract TipoTransporte getTipoTransporte();

}


