import org.junit.jupiter.api.Test;
import organizacion.Organizacion;
import organizacion.TipoOrganizacion;
import territorio.SectorTerritorial;
import territorio.TipoSectorTerritorial;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class SectorTerritorialTest {
  Organizacion unaOrganizacion = new Organizacion(
      "texto1",
      TipoOrganizacion.INSTITUCION,
      "texto2",
      "texto3");
  Organizacion organizacionMock = mock(Organizacion.class);
  List <Organizacion> organizaciones = new ArrayList<>();
  SectorTerritorial unSectorTerritorial = new SectorTerritorial(new ArrayList<>(), TipoSectorTerritorial.PROVINCIA);

  @Test
  void elTotalHCDeUnSectorTerritorialDeberiaSerLaSumaDeLasOrgsDeEseSector(){
    unSectorTerritorial.incorporarOrganizacion(organizacionMock);
    assertEquals(unSectorTerritorial.totalHC(),0);
    when(organizacionMock.calcularHC()).thenReturn(2000.0);
    assertEquals(unSectorTerritorial.totalHC(),2000.0);
  }

}
