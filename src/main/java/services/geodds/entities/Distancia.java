package services.geodds.entities;

public class Distancia {
  public double valor;
  public String unidad;

  public Distancia(double valor) {
    this.valor = valor;
  }

  public String getUnidad() {
    return unidad;
  }
  
  public double getValor() {
    return valor;
  }

  public void setUnidad(String unidad) {
    this.unidad = unidad;
  }

  public void setValor(double valor) {
    this.valor = valor;
  }
}
