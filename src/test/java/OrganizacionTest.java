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
  /*
  @Test
  public void elImpactoDeUnMiembroDeberiaSer100MultiplicadoPorElHCMDivididoPorElHCO() {
    Organizacion organizacionMock = mock(Organizacion.class);
    //Miembro juancito = generarMiembro("juan", "Nitales", 42222222, TipoDocumento.DNI);
    Miembro miembroMock = mock(Miembro.class);
    when(miembroMock.calcularHCTotal()).thenReturn(100.0);
    when(organizacionMock.calcularHC()).thenReturn(2000.0);
    assertEquals(organizacionMock.calcularHC(), 2000.0);
    assertEquals(miembroMock.calcularHCTotal(), 100.0);
    verify(organizacionMock,times(1)).calcularHC();
    verify(miembroMock,times(1)).calcularHCTotal();
    assertEquals(5.0,organizacionMock.impactoDeMiembro(miembroMock));
  // Miembro juan = new MiembroBuilder();
  } // doAnswer(invocation -> 12).when(mock).doSomething()
*/
    // doAnswer(invocation -> ((String)invocation.getArgument(1)).length())
    //     .when(mock).doSomething(anyString(), anyString(), anyString());

    // when(sistemaEnvio.envioSeguro(any(), any())).thenAnswer(invocation -> {
    //      Direccion direccion = (Direccion) invocation.getArgument(1);
    //      direccion.marcarComoValida();
    //      return "CODSEG01";
    //    });
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
    Organizacion organizacionMock = mock(Organizacion.class);
    when(organizacionMock.calcularHC()).thenReturn(2000.0);
    List<Miembro> miembros = new ArrayList<>();
    miembros.add(jorgito);
    when(organizacionMock.getMiembros()).thenReturn(miembros);
    assertEquals(2000,organizacionMock.indicadorHC_Miembros());
  }

  @Test
  public void elIndicadorHC_MiembrosEnSectorEsElHCDivididoLaCantidadMiembrosEnEseSector() {
    Organizacion organizacionMock = mock(Organizacion.class);
    when(organizacionMock.calcularHC()).thenReturn(2000.0);
    List<Miembro> miembros = new ArrayList<>();
    miembros.add(jorgito);
    Sector unSector = new Sector("Ventas", miembros);
    organizacionMock.incorporarSector(unSector);
    assertEquals(2000,organizacionMock.indicadorHC_MiembrosEnSector(unSector));
  } // when(organizacionMock.calcularHC()).thenAnswer(lambda);
  //

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




