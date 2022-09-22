import org.junit.jupiter.api.Test;
import organizacion.Organizacion;
import organizacion.TipoOrganizacion;
import territorio.SectorTerritorial;
import territorio.TipoSectorTerritorial;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;


public class SectorTerritorialTest {
  Organizacion unaOrganizacion = new Organizacion(
      "texto1",
      TipoOrganizacion.INSTITUCION,
      "texto2",
      "texto3", new ArrayList<>());
  Organizacion orgSpy = spy(unaOrganizacion);
  Organizacion orgSpy2 = spy(unaOrganizacion);
  List<Organizacion> organizaciones = new ArrayList<>();
  SectorTerritorial unSectorTerritorial = new SectorTerritorial(new ArrayList<>(), TipoSectorTerritorial.PROVINCIA);

  @Test
  void elTotalHCDelSectorEsLaSumaDeLasOrganizacionesDelMismo() {

    YearMonth periodo = YearMonth.of(2000, 12);
    unSectorTerritorial.incorporarOrganizacion(orgSpy);
    unSectorTerritorial.incorporarOrganizacion(orgSpy2);

    when(orgSpy.calcularHCTotal(periodo)).thenReturn(2000D);
    when(orgSpy2.calcularHCTotal(periodo)).thenReturn(1500D);
    assertEquals(3500D, unSectorTerritorial.totalHC(periodo));
  }
}
