import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import organizacion.*;
import transporte.Trayecto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SectorTest {

  Miembro miembro1 = mock(Miembro.class);
  Miembro miembro2 = mock(Miembro.class);

  List<Miembro> miembros = new ArrayList<>();

  Trayecto trayecto1 = mock(Trayecto.class);
  Trayecto trayecto2 = mock(Trayecto.class);

  List<Trayecto> trayectos = new ArrayList<>();

  Sector sector = new Sector("sector prueba", miembros);

  @BeforeEach
  public void init() {
    miembros.add(miembro1);
    miembros.add(miembro2);

    trayectos.add(trayecto1);
    trayectos.add(trayecto2);

    when(miembro1.getTrayectos()).thenReturn(trayectos);
    when(miembro2.getTrayectos()).thenReturn(trayectos);

    when(trayecto1.calcularHC()).thenReturn(300.3);
    when(trayecto2.calcularHC()).thenReturn(200.2);
  }

  @Test
  public void unMiembroSeVinculaConUnSector() {
    Organizacion onu = new Organizacion("texto1", TipoOrganizacion.INSTITUCION, "texto2", "texto3", new ArrayList<>());
    Sector compras = new Sector("Compras", new ArrayList<>());
    onu.incorporarSector(compras);
    Miembro jorgito = generarMiembro("jorge", "Nitales", 42222222, TipoDocumento.DNI);
    Solicitud nuevaSolicitud = new Solicitud(jorgito, compras);
    jorgito.solicitarVinculacion(onu, nuevaSolicitud);
    onu.procesarVinculacion(true);
    assertEquals(onu.getSectores().stream().
        filter(sector -> sector.getNombre().equals("Compras")).
        collect(Collectors.toList()).
        get(0).
        getMiembros().
        size(), 1);
  }

  @Test
  public void alObtenerTrayectosDeLosMiembrosNoObtengoLosRepetidos() {

    assertEquals((int) sector.getTrayectosDeMiembros().count(), 2);

  }

  @Test
  public void elHCTotalDeLosMiembrosEsLaSumaDelHCDeSusTrayectosSinRepetidos() {

    assertEquals(sector.calcularHCTotalDeMiembros(), 500.5);
  }

  @Test
  public void elHCPromedioPorMiembroEsElTotalSobreLaCantidadDeMiembros() {

    assertEquals(500.5 / 2, sector.calcularPromedioHCPorMiembro());

  }

  public Miembro generarMiembro(String nombre,
                                String apellido,
                                int documento,
                                TipoDocumento unTipo) {
    return MiembroTest.generarMiembro(nombre, apellido, documento, unTipo);
  }
}