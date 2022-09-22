package transporte;

import linea.PuntoUbicacion;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "TRAYECTO")
public class Trayecto {
  @Id
  @GeneratedValue
  @Column(name = "ID_TRAYECTO")
  Long id;

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(name = "TRAMO_POR_TRAYECTO")
  private List<Tramo> tramos = new ArrayList<>();

  public Trayecto(List<Tramo> tramos) {
    this.tramos = tramos;
  }

  public void setTramos(List<Tramo> tramos) {
    this.tramos = tramos;
  }

  public void agregarTramo(Tramo unTramo) {
    this.tramos.add(unTramo);
  }

  /*
   * public void setOrigen(String origen) { this.origen = origen; }
   */

  public PuntoUbicacion getOrigen() {
    return tramos.get(0).getPuntoOrigen();
  }

  public PuntoUbicacion getDestino() {
    return tramos.get(tramos.size() - 1).getPuntoDestino();
  }

  /*
   * public void setDestino(String destino) { this.destino = destino; }
   */

  public double distanciaTotal() {
    return tramos.stream().mapToDouble(Tramo::distanciaTramo).sum();
  }

  public double calcularHC() {
    return tramos.stream().mapToDouble(Tramo::calcularHc).sum();
  }
}
