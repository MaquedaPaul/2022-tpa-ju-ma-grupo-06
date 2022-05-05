import static org.junit.jupiter.api.Assertions.*;

import exceptions.NoSeAceptaVinculacion;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class SectorTest {

  @Test
  public void unMiembroSeVinculaConUnSector() {
    Organizacion onu = new Organizacion("texto1", TipoOrganizacion.INSTITUCION, "texto2", "texto3");
    onu.crearSector("Sector",generarListaVaciaDeMiembros());
    generarPersona().solicitarVinculacion(onu, "Sector");
    assertEquals(onu.sectores.stream().filter(sector -> sector.getNombre().equals("Sector")).collect(Collectors.toList()).get(0).getMiembros().size(), 1);
  }

  @Test
  public void siElSectorNoPerteneceALaOrganizacionNoSeVincula() {
    Organizacion onu = new Organizacion("texto1", TipoOrganizacion.INSTITUCION, "texto2", "texto3");
    assertThrows(NoSeAceptaVinculacion.class,( ()-> generarPersona().solicitarVinculacion(onu, "Sector")));
  }

  private List<Miembro> generarListaVaciaDeMiembros() {
    return new ArrayList<>();
  }

  private Miembro generarPersona() {
    List<Organizacion> listaOrganizacionVacia = new ArrayList<>();
    List<Trayecto> listaTrayectoVacia = new ArrayList<>();
    return new Miembro("texto1", "texto2", TIpoDocumento.DNI, 123, listaOrganizacionVacia, listaTrayectoVacia);
  }
}