package reporte;

import linea.PuntoUbicacion;
import mediciones.Medicion;
import mediciones.MedicionMensual;
import miembro.Miembro;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import organizacion.Organizacion;
import organizacion.Sector;
import organizacion.TipoDocumento;
import organizacion.TipoOrganizacion;
import organizacion.periodo.PeriodoMensual;
import reporte.itemsreportes.EvolucionHCOrganizacion;
import reporte.itemsreportes.HCPorTipoOrganizacion;
import repositorios.RepoOrganizacion;
import tipoconsumo.TipoConsumo;
import transporte.Trayecto;

import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReporteOrganizacionesTest {
  PeriodoMensual eneroDe2022 = new PeriodoMensual(LocalDate.of(2022, 1, 1));
  PeriodoMensual diciembreDe2022 = new PeriodoMensual(LocalDate.of(2022, 12, 1));
  Organizacion mockOrganizacion = mock(Organizacion.class);
  Organizacion mockOtraOrganizacion = mock(Organizacion.class);
  RepoOrganizacion mockRepoOrganizacion = mock(RepoOrganizacion.class);
  ReporteOrganizaciones reporteOrganizaciones = new ReporteOrganizaciones(mockRepoOrganizacion);
  Miembro miembro = new Miembro("juan", "blanco", TipoDocumento.DNI,3,new ArrayList<>());
  Organizacion organizacion = new Organizacion(
      "texto1",TipoOrganizacion.INSTITUCION, new PuntoUbicacion(1,"San Juan",333), "texto3", new ArrayList<>()
  );

  Organizacion spyOrgizacion = spy(organizacion);
  Sector compras = new Sector("Compras", new ArrayList<>());

  {

    organizacion.incorporarSector(compras);
    compras.admitirMiembro(miembro);
    spyOrgizacion.incorporarSector(compras);
  }

  @Test
  public void laSumaDeLosHcDe2OrganizacionDeUnMismoTipoEs1350() {
    when(mockOrganizacion.calcularHCTotalEntre(eneroDe2022, diciembreDe2022)).thenReturn(450.0);
    when(mockOtraOrganizacion.calcularHCTotalEntre(eneroDe2022, diciembreDe2022)).thenReturn(900.0);
    when(mockRepoOrganizacion.getOrganizacionesDelTipo(TipoOrganizacion.GUBERNAMENTAL)).thenReturn(Arrays.asList(mockOrganizacion, mockOtraOrganizacion));
    when(mockRepoOrganizacion.getOrganizacionesDelTipo(TipoOrganizacion.INSTITUCION)).thenReturn(new ArrayList<>());
    when(mockRepoOrganizacion.getOrganizacionesDelTipo(TipoOrganizacion.ONG)).thenReturn(new ArrayList<>());
    when(mockRepoOrganizacion.getOrganizacionesDelTipo(TipoOrganizacion.EMPRESA)).thenReturn(new ArrayList<>());
    List<HCPorTipoOrganizacion> hcPorTipoOrganizacions =
        reporteOrganizaciones.hcPorTipoOrganizacion(eneroDe2022, diciembreDe2022);
    assertEquals(hcPorTipoOrganizacions.stream().mapToDouble(HCPorTipoOrganizacion::getHc).sum(), 1350.0);
  }

  @Test
  public void elCalculoDeLaEvolucionHcEntreEneroYDiciembreCalcula12Meses() {
    List<EvolucionHCOrganizacion> evolucionHCOrganizacion =
        reporteOrganizaciones.evolucionHCEntre(mockOrganizacion, eneroDe2022, diciembreDe2022);
    assertEquals(evolucionHCOrganizacion.size(), 12);
  }

  /*
  @Test
  public void testeando() {
    Trayecto trayecto1 = mock(Trayecto.class);
    Trayecto trayecto2 = mock(Trayecto.class);
    miembro.registrarTrayecto(trayecto1);
    miembro.registrarTrayecto(trayecto2);
    when(trayecto1.calcularHC()).thenReturn(120.0);
    when(trayecto2.calcularHC()).thenReturn(280.0);
    miembro.registrarTrayecto(trayecto1);
    miembro.registrarTrayecto(trayecto2);

    Medicion medicionEnero2021 = new MedicionMensual(spyOrgizacion,mock(TipoConsumo.class),LocalDate.of(2022, 1, 1),100);
    List<Medicion> mediciones = new ArrayList<>();
    mediciones.add(medicionEnero2021);

    when(spyOrgizacion.getMediciones()).thenReturn(mediciones);

    assertEquals(reporteOrganizaciones.composicionHC(spyOrgizacion, eneroDe2022), 480);
  }

   */
}