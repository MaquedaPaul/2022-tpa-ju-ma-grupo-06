import exceptions.NoSeAceptaVinculacion;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class OrganizacionTest {

  @Test
  public void laOrganizacionIncorporaUnSector() {
    Organizacion onu = new Organizacion("texto1", TipoOrganizacion.INSTITUCION, "texto2", "texto3");
    Sector compras = new Sector("Compras", new ArrayList<>());
    onu.incorporarSector(compras);
    assertEquals(onu.getSectores().size(), 1);
  }

  @Test
  public void laOrganizacionNoAceptaVinculacion() {
    Organizacion onu = new Organizacion("texto1", TipoOrganizacion.INSTITUCION, "texto2", "texto3");
    Sector compras = new Sector("Compras", new ArrayList<>());
    onu.incorporarSector(compras);
    Miembro jorgito = generarMiembro("jorge", "Nitales", 42222222, TIpoDocumento.DNI);
    Solicitud nuevaSolicitud = new Solicitud(jorgito, compras);
    jorgito.solicitarVinculacion(onu, nuevaSolicitud);
    assertEquals(onu.getSolicitudes().size(), 1);
    assertThrows(NoSeAceptaVinculacion.class,() -> onu.procesarVinculacion(false));
    assertEquals(onu.getSolicitudes().size(), 0);
    assertFalse(onu.sectores.stream().
        filter(sector -> sector.getNombre().equals("Compras")).
        collect(Collectors.toList()).
        get(0).
        getMiembros().
        contains(jorgito));
  }

  public Miembro generarMiembro(String nombre,
                                String apellido,
                                int documento,
                                TIpoDocumento unTipo) {
    MiembroTest testMiembro = new MiembroTest();
    return testMiembro.generarMiembro(nombre, apellido, documento, unTipo);
  }
}




