import Linea.Parada;

import java.util.ArrayList;
import java.util.List;

public class Trayecto{
  private List<Tramo> tramos = new ArrayList<>();

  public void setTramos(List<Tramo> tramos) {
    this.tramos = tramos;
  }

  public void agregarTramo(Tramo unTramo){
    this.tramos.add(unTramo);
  }

  /*public void setOrigen(String origen) {
    this.origen = origen;
  }*/

  public Parada getOrigen() {
    return tramos.get(0).getPuntoOrigen();
  }

  public Parada getDestino() {
    return tramos.get(tramos.size()-1).getPuntoDestino();
  }

  /*public void setDestino(String destino) {
    this.destino = destino;
  }*/

}