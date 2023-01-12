package services.geodds.entities;

import global.Unidad;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Distancia {

  public double valor;
  public Unidad unidad;

  public Distancia(double valor) {
    this.valor = valor;
  }

}
