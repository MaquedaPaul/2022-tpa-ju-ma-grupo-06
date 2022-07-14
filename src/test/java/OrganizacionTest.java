import exceptions.NoSeAceptaVinculacion;
import exceptions.NoSeEncuentraException;
import org.junit.jupiter.api.Test;
import organizacion.*;
import notificaciones.Contacto;

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
  public void elImpactoDeUnMiembroDeberiaSer100MultiplicadoPorElHCMDivididoPorElHCO() {

    when(spyjorgito.calcularHCTotal()).thenReturn(100.0);
    when(spyOnu.calcularHC()).thenReturn(2000.0);
    verify(spyOnu,times(1)).calcularHC();
    verify(spyjorgito,times(1)).calcularHCTotal();
    assertEquals(5.0,spyOnu.impactoDeMiembro(spyjorgito));
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
  public void elIndicadorHC_MiembrosEsElHCDivididoLaCantidadMiembros() {

    when(spyOnu.calcularHC()).thenReturn(2000.0);
    List<Miembro> miembros = new ArrayList<>();
    miembros.add(jorgito);
    when(spyOnu.getMiembros()).thenReturn(miembros);
    assertEquals(2000,spyOnu.indicadorHC_Miembros());
  }

  @Test
  public void elIndicadorHC_MiembrosEnSectorEsElHCDivididoLaCantidadMiembrosEnEseSector() {
    when(spyOnu.calcularHC()).thenReturn(2000.0);
    List<Miembro> miembros = new ArrayList<>();
    miembros.add(jorgito);
    Sector unSector = new Sector("Ventas", miembros);
    spyOnu.incorporarSector(unSector);
    assertEquals(2000,spyOnu.indicadorHC_MiembrosEnSector(unSector));
  }


  @Test
  public void deberiaPoderCargarseUnContacto(){
    Contacto unContacto = new Contacto(onu,"Pedrito","pedrito@gmail.com","1122653678");
    assertEquals(onu.getContactos().size(),0);
    onu.cargarContacto(unContacto);
    assertEquals(onu.getContactos().size(),1);
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




