import java.util.ArrayList;
import java.util.List;

public class Trayecto{
  private List<Tramo> tramos = new ArrayList<>();
  private String origen;
  private String destino;

  public void setTramos(List<Tramo> tramos) {
    this.tramos = tramos;
  }

  public void agregarTramo(Tramo unTramo){
    this.tramos.add(unTramo);
  }

  public void setOrigen(String origen) {
    this.origen = origen;
  }

  public String getOrigen() {
    return origen;
  }

  public String getDestino() {
    return destino;
  }

  public void setDestino(String destino) {
    this.destino = destino;
  }

}