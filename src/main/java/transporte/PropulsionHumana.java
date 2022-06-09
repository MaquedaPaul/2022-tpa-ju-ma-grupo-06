package transporte;

import java.io.IOException;
import linea.PuntoUbicacion;
import services.geodds.ServicioGeodds;
import services.geodds.entities.Distancia;

public class PropulsionHumana extends TransportePrivado {

  private String herramientaUtilizada;

  public PropulsionHumana(String herramientaUtilizada) {
    this.herramientaUtilizada = herramientaUtilizada;
  }

  public String getHerramientaUtilizada() {
    return herramientaUtilizada;
  }

}
