package utils;

import lombok.Getter;
import lombok.Setter;
import services.geodds.ServicioDistancia;

@Getter
@Setter
public class ServiceLocator {

  private static ServiceLocator instance;

  private ServicioDistancia servicioDistancia;

  private int diasDeTrabajo = 20;

  private ServiceLocator() {
  }

  public static ServiceLocator getInstance() {
    if (instance == null) {
      instance = new ServiceLocator();
    }

    return instance;
  }

}
