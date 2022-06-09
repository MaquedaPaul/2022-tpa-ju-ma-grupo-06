import services.geodds.ServicioGeodds;
import services.geodds.entities.Distancia;

import java.io.IOException;

public class EjemploDeUsoAPI {


    public static void main(String[] args) throws IOException {

      ServicioGeodds servicioGeodds = ServicioGeodds.getInstancia();
      Distancia distancia = servicioGeodds.distancia(156,"dante",200,384,"san lorenzo",600);

      System.out.println(distancia.valor);
    }
  }
