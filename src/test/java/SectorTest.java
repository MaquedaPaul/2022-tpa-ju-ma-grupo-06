import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class SectorTest {
  @Test
  public void unMiembroSeVinculaConUnSector() {
    Organizacion onu = new Organizacion("texto1", TipoOrganizacion.INSTITUCION, "texto2", "texto3");
    Sector nuevoSector = new Sector(generarListaVaciaDeMiembros());
    generarPersona().solicitarVinculacion(onu, nuevoSector);
    assertEquals(nuevoSector.getMiembros().size(), 1);
  }

  private List<Miembro> generarListaVaciaDeMiembros() {
    List<Miembro> listaMiembro = new ArrayList<>();
    return listaMiembro;
  }

  private Miembro generarPersona() {
    List<Organizacion> listaOrganizacionVacia = new ArrayList<>();
    List<Trayecto> listaTrayectoVacia = new ArrayList<>();
    return new Miembro("texto1", "texto2", TIpoDocumento.DNI, 123, listaOrganizacionVacia, listaTrayectoVacia);
  }
}