package utils;

import lombok.Getter;
import lombok.Setter;
import transporte.Transporte;

import java.util.List;

@Getter
@Setter
public class CategoriaEInstanciasDeTransporte {
  private final String categoria;
  private final List<Transporte> transportes;

  public CategoriaEInstanciasDeTransporte(String categoria, List<Transporte> transportes) {
    this.categoria = categoria;
    this.transportes = transportes;
  }
}
