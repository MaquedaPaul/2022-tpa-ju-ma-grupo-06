import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class OrganizacionTest {

  @Test
  public void seCreaUnaOrganizacionYLuegoLaOrganizacionCreaUnSector() {
    Organizacion onu = new Organizacion("texto1", TipoOrganizacion.INSTITUCION, "texto2",
                                        generarListaSectorVacia(), "texto3");
    onu.crearSector(generarListaVaciaDeMiembros());
    assertEquals(onu.getSectores().size(), 1);
  }

  private List<Miembro> generarListaVaciaDeMiembros() {
    List<Miembro> listaMiembro = new ArrayList<>();
    return listaMiembro;
  }

  private List<Sector> generarListaSectorVacia() {
    List<Sector> sectores = new ArrayList<>();
    return sectores;
  }
}


