package server.bootstraps;

import linea.PuntoUbicacion;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import transporte.*;

public class BootstrapsTramosApi implements WithGlobalEntityManager {
  public static void init() {

    // PUNTOS DE UBICACION -> ACEPTADOS POR LA API

    // LocalidadID -> Numero mayor a 1 y menor o igual que 6326
    // Calle -> Se puede poner cualquier String, la Api lo toma
    // Numero -> Se puede poner cualquier String, la Api lo toma

    PuntoUbicacion puntoUbicacionIda = new PuntoUbicacion(1,"San Lorenzo",6153);
    PuntoUbicacion puntoUbicacionDestino = new PuntoUbicacion(1,"25 de mayo",4224);

    PuntoUbicacion puntoUbicacionIda2 = new PuntoUbicacion(3,"Dante",2222);
    PuntoUbicacion puntoUbicacionDestino2 = new PuntoUbicacion(180,"Salguero",3000);

    // TRANSPORTE

    Transporte transporteUtilizado = new PropulsionHumana("Pies");
    Transporte transporteUtilizado2 = new PropulsionHumana("Monopatin");

    // TRAMOS

    Tramo tramo1 = new Tramo(puntoUbicacionIda,puntoUbicacionDestino,transporteUtilizado);
    Tramo tramo2 = new Tramo(puntoUbicacionIda2,puntoUbicacionDestino2,transporteUtilizado2);

    // TRAYECTOS

    BuilderTrayecto builder = new BuilderTrayecto();
    builder.agregarTramo(tramo1);
    builder.agregarTramo(tramo2);
    Trayecto trayecto = builder.build();
  }

  public void persistir(Object object) {
    entityManager().getTransaction().begin();
    entityManager().persist(object);
    entityManager().getTransaction().commit();
  }

}
