import java.io.IOException;
import services.geodds.ServicioGeodds;
import services.geodds.entities.Distancia;


public class EjemploDeUso {


  public static void main(String[] args) throws IOException {

    ServicioGeodds servicioGeodds = ServicioGeodds.getInstancia();

    Distancia distancia = servicioGeodds.distancia(156, "dante", 200, 384, "san lorenzo", 600);

    System.out.println(distancia);
  }

}
