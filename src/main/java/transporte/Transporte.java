package transporte;

import java.io.IOException;
import linea.PuntoUbicacion;

public abstract class Transporte {
  Combustible combustible;

  public abstract double distanciaEntre(PuntoUbicacion origen, PuntoUbicacion destino) throws IOException;

  public double calcularHc() {
    return combustible.calcularHc();
  }

  public Combustible getCombustible(){ return this.combustible;}

  public void setCombustible(Combustible combustible){ this.combustible = combustible; }

}


