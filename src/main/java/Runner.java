import linea.*;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import transporte.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;


public class Runner {

  public static void main(String[] args) {
    EntityManager em = PerThreadEntityManagers.getEntityManager();
    EntityTransaction et = em.getTransaction();

    et.begin();
    Coordenada coordenadaIda = new Coordenada(15,15);
    Coordenada coordenadaVuelta = new Coordenada(45,75);
    PuntoUbicacion punto1 = new PuntoUbicacion(12,"salta",157, coordenadaIda);
    PuntoUbicacion punto2 = new PuntoUbicacion(12,"salta",157, coordenadaVuelta);
    List<Parada> paradasIda = new ArrayList<>();
    List<Parada> paradasVuelta = new ArrayList<>();
    paradasIda.add(new Parada(700, coordenadaIda, true));
    paradasVuelta.add(new Parada(700, coordenadaIda, true));
    LineaTransporte nuevaLinea =
        new LineaTransporte(TipoTransporte.COLECTIVO, "441", paradasIda, paradasVuelta);
    TransportePublico transporte = new TransportePublico(nuevaLinea, 20);
    em.persist(new Tramo(punto1, punto2, new VehiculoParticular(TipoVehiculo.AUTO, 20)));
    em.persist(new Tramo(punto1, punto2, new ServicioContratado(TipoVehiculo.AUTO, 20)));
    em.persist(transporte);
    et.commit();
  }

}