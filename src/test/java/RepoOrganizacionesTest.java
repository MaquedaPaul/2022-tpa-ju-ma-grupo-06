import linea.*;
import notificaciones.Contacto;
import org.junit.jupiter.api.Test;
import organizacion.*;
import transporte.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class RepoOrganizacionesTest {

    Coordenada coordenadaIda = new Coordenada(15,15);
    Coordenada coordenadaVuelta = new Coordenada(45,75);
    PuntoUbicacion punto1 = new PuntoUbicacion(12,"salta",157, coordenadaIda);
    PuntoUbicacion punto2 = new PuntoUbicacion(12,"salta",157, coordenadaVuelta);
    List<Parada> paradasIda = new ArrayList<>();
    List<Parada> paradasVuelta = new ArrayList<>();
    LineaTransporte nuevaLinea =
            new LineaTransporte(TipoTransporte.COLECTIVO, "441", paradasIda, paradasVuelta);
    TransportePublico transporte = new TransportePublico(nuevaLinea, 20);
    List<Trayecto> trayectos = new ArrayList<>();
    Tramo unTramo = new Tramo(punto1, punto2, transporte);
    Set<Tramo> tramos = new HashSet<>();
    Trayecto unTrayecto = new Trayecto(tramos);
    Miembro unMiembro = new Miembro("Pedro","Rodriguez", TipoDocumento.DNI,43409129,trayectos);
    List<Miembro> miembros = new ArrayList<>();

    List<Contacto> contactos = new ArrayList<>();
    Contacto unContacto = new Contacto("Pedro", "Pedrito@gmail.com","1135032912");
    Sector unSector = new Sector("Ventas",miembros);

    // ORGANIZACIONES
    Organizacion unaOrganizacion = new Organizacion("Pedrito SRL", TipoOrganizacion.EMPRESA,"Argentina","unaClasificacion",contactos);
    Organizacion OrgGubernamental = new Organizacion("Pedrito SRL2", TipoOrganizacion.GUBERNAMENTAL,"Argentina","unaClasificacion",contactos);
    Organizacion OrgGubernamental2 = new Organizacion("Pedrito SRL3", TipoOrganizacion.GUBERNAMENTAL,"Argentina","unaClasificacion",contactos);
    Organizacion unaOrganizacionAgregada = new Organizacion("AGREGADO", TipoOrganizacion.EMPRESA,"Argentina","unaClasificacion",contactos);

    // LISTA DE ORGANIZACIONES

    List<Organizacion> listadoOrganizacionesGubernamentales = new ArrayList<>();

    {
        paradasIda.add(new Parada(700, coordenadaIda, true));
        paradasVuelta.add(new Parada(700, coordenadaVuelta, false));
        tramos.add(unTramo);
        trayectos.add(unTrayecto);
        miembros.add(unMiembro);
        contactos.add(unContacto);
        unaOrganizacion.incorporarSector(unSector);
        listadoOrganizacionesGubernamentales.add(OrgGubernamental);
        listadoOrganizacionesGubernamentales.add(OrgGubernamental2);
    }
    @Test
    public void sePuedeEliminarUnaOrganizacion() {
        RepoOrganizacion.getInstance().agregarOrganizacion(unaOrganizacionAgregada);
        List<Organizacion> organizaciones = RepoOrganizacion.getInstance().getOrganizaciones();
        assertEquals(organizaciones.size(), 1);
        RepoOrganizacion.getInstance().eliminarOrganizacion(unaOrganizacionAgregada);
        organizaciones = RepoOrganizacion.getInstance().getOrganizaciones();
        assertEquals(organizaciones.size(), 0);
    }

    @Test
    public void unaOrganizacionCargadaEnLaBaseDeberiaPoderEncontrarse() {
        RepoOrganizacion.getInstance().agregarOrganizacion(unaOrganizacion);
        RepoOrganizacion.getInstance().agregarOrganizacion(unaOrganizacionAgregada);
        List<Organizacion> organizaciones = RepoOrganizacion.getInstance().getOrganizaciones();
        int longitud = organizaciones.size();
        Organizacion match = organizaciones.stream()
            .filter(unaOrganizacion -> unaOrganizacion.getRazonSocial().equals("AGREGADO")).collect(Collectors.toList()).get(0);
        //assertEquals(match, unaOrganizacionAgregada);
        assertTrue(RepoOrganizacion.getInstance().estaPersistido(unaOrganizacion));
    }

    @Test
    public void sePuedeFiltrarPorTipoDeOrganizacionGubernamental() {
        List<Organizacion> listadoOrganizacionesGubernamentales = new ArrayList<>();
        listadoOrganizacionesGubernamentales.add(OrgGubernamental);
        listadoOrganizacionesGubernamentales.add(OrgGubernamental2);
        RepoOrganizacion.getInstance().agregarOrganizacion(OrgGubernamental);
        RepoOrganizacion.getInstance().agregarOrganizacion(unaOrganizacion);
        RepoOrganizacion.getInstance().agregarOrganizacion(OrgGubernamental2);
        List<Organizacion> listadoDeLaBase = RepoOrganizacion.getInstance()
            .getOrganizaciones()
            .stream()
            .filter(organizacion -> organizacion.getTipo().equals(TipoOrganizacion.GUBERNAMENTAL)).collect(Collectors.toList());
        assertEquals(listadoOrganizacionesGubernamentales, listadoDeLaBase);
    }
}
