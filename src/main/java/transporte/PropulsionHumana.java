package transporte;

import linea.PuntoUbicacion;
import services.geodds.ServicioGeodds;
import services.geodds.entities.Distancia;

import java.io.IOException;

public class PropulsionHumana extends TransportePrivado {
  ServicioGeodds servicioGeodds = ServicioGeodds.getInstancia();
  private String herramientaUtilizada;

  public PropulsionHumana(String herramientaUtilizada) {
    this.herramientaUtilizada = herramientaUtilizada;
  }

  public String getHerramientaUtilizada() {
    return herramientaUtilizada;
  }

}
