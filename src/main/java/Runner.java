import linea.*;
import notificaciones.Contacto;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import organizacion.*;
import registrohc.RegistroHCOrganizacion;
import transporte.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.YearMonth;
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
            //new VehiculoParticular(TipoVehiculo.AUTO)
            //


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


    Organizacion OrgGubernamental = new Organizacion("Pedrito SRL2", TipoOrganizacion.GUBERNAMENTAL,"Argentina","unaClasificacion",contactos);
    Organizacion OrgGubernamental2 = new Organizacion("Pedrito SRL3", TipoOrganizacion.GUBERNAMENTAL,"Argentina","unaClasificacion",contactos);
    Organizacion unaOrganizacionAgregada = new Organizacion("AGREGADO", TipoOrganizacion.EMPRESA,"Argentina","unaClasificacion",contactos);

    ///////////////////////////////
    et.begin();

    em.persist(new Tramo(punto1, punto2, new VehiculoParticular(TipoVehiculo.AUTO, 20)));
    em.persist(new Tramo(punto1, punto2, new ServicioContratado(TipoVehiculo.AUTO, 20)));
    em.persist(transporte);
    em.persist(unaOrganizacion);
    RepoOrganizacion.getInstance().agregarOrganizacion(OrgGubernamental);
    RepoOrganizacion.getInstance().agregarOrganizacion(unaOrganizacion);
    RepoOrganizacion.getInstance().agregarOrganizacion(OrgGubernamental2);

    List<Organizacion> orgs = RepoOrganizacion.getInstance().getOrganizaciones();
    RegistroHCOrganizacion registroHCOrganizacion1 = new RegistroHCOrganizacion(orgs.get(0),333L,20L,2020,3);
    RegistroHCOrganizacion registroHCOrganizacion2 = new RegistroHCOrganizacion(orgs.get(1),213L,330L,2020,8);
    List<HC_Por_Tipo_Organizacion> lista_hc_por_tipo = RepoOrganizacion.getInstance().HCPorTipoOrganizacion(YearMonth.of(2018,1),YearMonth.of(2021,12));
    et.commit();
    for (HC_Por_Tipo_Organizacion elemento: lista_hc_por_tipo) {
      System.out.println(elemento.getHc() + elemento.getUnTipo().toString());
    }

    /*
    List<Organizacion> listadoDeLaBase = RepoOrganizacion.getInstance().filtrarPorTipoOrganizacion(TipoOrganizacion.GUBERNAMENTAL);
    System.out.println("LISTA: " + listadoDeLaBase.get(0).getTipo());
    em.refresh(OrgGubernamental);
    em.refresh(OrgGubernamental2);
    System.out.println("ID: " + OrgGubernamental.getId());
    System.out.println("ID: " + OrgGubernamental2.getId());
     */
  }

}