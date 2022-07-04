package transporte;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import linea.PuntoUbicacion;


public class Trayecto {

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
}
