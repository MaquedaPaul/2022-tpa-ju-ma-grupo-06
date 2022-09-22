import linea.*;
import notificaciones.Contacto;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import organizacion.*;
import transporte.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;


public class Runner {

  public static void main(String[] args) {
    EntityManager em = PerThreadEntityManagers.getEntityManager();
    EntityTransaction et = em.getTransaction();
    ///////////////////////
    Coordenada coordenadaIda = new Coordenada(15,15);
    Coordenada coordenadaVuelta = new Coordenada(45,75);
    PuntoUbicacion punto1 = new PuntoUbicacion(12,"salta",157, coordenadaIda);
    PuntoUbicacion punto2 = new PuntoUbicacion(12,"salta",157, coordenadaVuelta);
    List<Parada> paradasIda = new ArrayList<>();
    List<Parada> paradasVuelta = new ArrayList<>();
    paradasIda.add(new Parada(700, coordenadaIda, true));
    paradasVuelta.add(new Parada(700, coordenadaVuelta, false));
    LineaTransporte nuevaLinea =
            new LineaTransporte(TipoTransporte.COLECTIVO, "441", paradasIda, paradasVuelta);
    TransportePublico transporte = new TransportePublico(nuevaLinea, 20);


    List<Trayecto> trayectos = new ArrayList<>();

    Tramo unTramo = new Tramo(punto1, punto2, transporte);
    List<Tramo> tramos = new ArrayList<>();
    tramos.add(unTramo);
    Trayecto unTrayecto = new Trayecto(tramos);
    trayectos.add(unTrayecto);


    Miembro unMiembro = new Miembro("Pedro","Rodriguez", TipoDocumento.DNI,43409129,trayectos);
    List<Miembro> miembros = new ArrayList<>();
    miembros.add(unMiembro);
    List<Contacto> contactos = new ArrayList<>();
    Contacto unContacto = new Contacto("Pedro", "Pedrito@gmail.com","1135032912");
    contactos.add(unContacto);
    Organizacion unaOrganizacion = new Organizacion("Pedrito SRL", TipoOrganizacion.EMPRESA,"Argentina","unaClasificacion",contactos);
    Sector unSector = new Sector("Ventas",miembros);
    unaOrganizacion.incorporarSector(unSector);
    ///////////////////////////////
    et.begin();

    em.persist(new Tramo(punto1, punto2, new VehiculoParticular(TipoVehiculo.AUTO, 20)));
    em.persist(new Tramo(punto1, punto2, new ServicioContratado(TipoVehiculo.AUTO, 20)));
    em.persist(transporte);
    em.persist(unaOrganizacion);
    et.commit();
  }

}