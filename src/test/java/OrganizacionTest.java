import exceptions.NoSeAceptaVinculacion;
import exceptions.NoSeEncuentraException;
import mediciones.Medicion;
import notificaciones.Contacto;
import org.junit.jupiter.api.Test;
import organizacion.*;
import transporte.Trayecto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrganizacionTest {

  Organizacion onu = new Organizacion("texto1", TipoOrganizacion.INSTITUCION, "texto2", "texto3", new ArrayList<>());
  Miembro jorgito = generarMiembro("jorge", "Nitales", 42222222, TipoDocumento.DNI);
  Organizacion spyOnu = spy(onu);
  Miembro spyjorgito = spy(jorgito);

  Medicion med1 = mock(Medicion.class);
  Medicion med2 = mock(Medicion.class);
  Medicion med3 = mock(Medicion.class);

  List<Medicion> mediciones = new ArrayList<>();

  Miembro miembro1 = mock(Miembro.class);
  Miembro miembro2 = mock(Miembro.class);

  List<Miembro> miembros = new ArrayList<>();

  Trayecto trayecto1 = mock(Trayecto.class);
  Trayecto trayecto2 = mock(Trayecto.class);
  Trayecto trayecto3 = mock(Trayecto.class);

  List<Trayecto> trayectos1 = new ArrayList<>();
  List<Trayecto> trayectos2 = new ArrayList<>();

  @Test
  public void laOrganizacionIncorporaUnSector() {

    Sector compras = new Sector("Compras", new ArrayList<>());
    onu.incorporarSector(compras);
    assertEquals(onu.getSectores().size(), 1);
  }

  @Test
  public void laOrganizacionNoAceptaVinculacion() {

    Sector compras = new Sector("Compras", new ArrayList<>());
    onu.incorporarSector(compras);

    Solicitud nuevaSolicitud = new Solicitud(jorgito, compras);
    jorgito.solicitarVinculacion(onu, nuevaSolicitud);
    assertEquals(onu.getSolicitudes().size(), 1);
    assertThrows(NoSeAceptaVinculacion.class, () -> onu.procesarVinculacion(false));
    assertEquals(onu.getSolicitudes().size(), 0);
    assertFalse(onu.getSectores().stream().
        filter(sector -> sector.getNombre().equals("Compras")).
        collect(Collectors.toList()).
        get(0).
        getMiembros().
        contains(jorgito));
  }

  //HCO = HC total de la organización
  //HCM = HC total del miembro

  @Test
  public void elHCMensualDeLosMiembrosEs2000() {

    Organizacion org = spy(onu);

    mediciones.add(med1);
    mediciones.add(med2);
    mediciones.add(med3);

    Sector sector1 = mock(Sector.class);
    Sector sector2 = mock(Sector.class);

    org.incorporarSector(sector1);
    org.incorporarSector(sector2);
    List<Sector> sectores = new ArrayList<>();
    sectores.add(sector1);
    sectores.add(sector2);
    miembros.add(miembro1);
    List<Miembro> otros = new ArrayList<>();
    otros.add(miembro2);
    when(sector1.getMiembros()).thenReturn(miembros);
    when(sector2.getMiembros()).thenReturn(otros);
    when(org.getSectores()).thenReturn(sectores);

    trayectos1.add(trayecto1);
    trayectos1.add(trayecto2);
    trayectos2.add(trayecto2);
    trayectos2.add(trayecto3);

    when(miembro1.getTrayectos()).thenReturn(trayectos1);
    when(miembro2.getTrayectos()).thenReturn(trayectos2);

    when(trayecto1.calcularHC()).thenReturn(40D);
    when(trayecto2.calcularHC()).thenReturn(30D);
    when(trayecto3.calcularHC()).thenReturn(30D);

    assertEquals(2000D, org.calcularHCTotalMensualDeMiembros());

  }

  @Test
  public void elHCAnualDeLosMiembrosEs24000() {

    Organizacion org = spy(onu);

    mediciones.add(med1);
    mediciones.add(med2);
    mediciones.add(med3);

    Sector sector1 = mock(Sector.class);
    Sector sector2 = mock(Sector.class);

    org.incorporarSector(sector1);
    org.incorporarSector(sector2);
    List<Sector> sectores = new ArrayList<>();
    sectores.add(sector1);
    sectores.add(sector2);
    miembros.add(miembro1);
    List<Miembro> otros = new ArrayList<>();
    otros.add(miembro2);
    when(sector1.getMiembros()).thenReturn(miembros);
    when(sector2.getMiembros()).thenReturn(otros);
    when(org.getSectores()).thenReturn(sectores);

    trayectos1.add(trayecto1);
    trayectos1.add(trayecto2);
    trayectos2.add(trayecto2);
    trayectos2.add(trayecto3);

    when(miembro1.getTrayectos()).thenReturn(trayectos1);
    when(miembro2.getTrayectos()).thenReturn(trayectos2);

    when(trayecto1.calcularHC()).thenReturn(40D);
    when(trayecto2.calcularHC()).thenReturn(30D);
    when(trayecto3.calcularHC()).thenReturn(30D);

    assertEquals(24000D, org.calcularHCTotalAnualDeMiembros());

  }

  @Test
  public void elImpactoDeJorgeEnJulio2021EsDel20Porciento() {

    trayectos1.add(trayecto2);
    trayectos1.add(trayecto3);

    when(spyjorgito.getTrayectos()).thenReturn(trayectos1);
    when(trayecto2.calcularHC()).thenReturn(300D);
    when(trayecto3.calcularHC()).thenReturn(100D);

    when(spyOnu.calcularHCTotal("07/2021")).thenReturn(40000D);
    // (100 * 20 * 400) / 40000 = 20
    assertEquals(20D, spyOnu.impactoDeMiembro(spyjorgito, "07/2021"));
  }

  @Test
  public void elImpactoDeJorgeEnTodo2021EsDel16Porciento() {

    trayectos1.add(trayecto2);
    trayectos1.add(trayecto3);

    when(spyjorgito.getTrayectos()).thenReturn(trayectos1);
    when(trayecto2.calcularHC()).thenReturn(300D);
    when(trayecto3.calcularHC()).thenReturn(100D);

    when(spyOnu.calcularHCTotal("2021")).thenReturn(600000D);
    // (100 * 12 * 20 * 400) / x = 16
    assertEquals(16D, spyOnu.impactoDeMiembro(spyjorgito, "2021"));
  }

  @Test
  public void unaOrganizacionNoPuedeAccederALosMiembrosQueNoLePertenecen() {
    List<Miembro> miembros = new ArrayList<>();
    miembros.add(jorgito);
    Sector compras = new Sector("Compras", miembros);
    assertThrows(NoSeEncuentraException.class, () -> onu.getMiembrosEnSector(compras));
    onu.incorporarSector(compras);
    assertEquals(onu.getMiembrosEnSector(compras), compras.getMiembros());
  }

  @Test
  public void deberiaPoderCargarseUnContacto() {
    Contacto unContacto = new Contacto(onu, "Pedrito", "pedrito@gmail.com", "1122653678");
    assertEquals(onu.getContactos().size(), 0);
    onu.cargarContacto(unContacto);
    assertEquals(onu.getContactos().size(), 1);
  }

  public static Miembro generarMiembro(String nombre,
                                       String apellido,
                                       int documento,
                                       TipoDocumento unTipo) {
    MiembroBuilder nuevoMiembro = new MiembroBuilder();
    nuevoMiembro.especificarNombre(nombre);
    nuevoMiembro.especificarApellido(apellido);
    nuevoMiembro.especificarNumeroDocumento(documento);
    nuevoMiembro.especificarTipoDocumento(unTipo);
    nuevoMiembro.especificarTrayectos(new ArrayList<>());
    return nuevoMiembro.construir();
  }
}




