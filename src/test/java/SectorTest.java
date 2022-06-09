import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import organizacion.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

class SectorTest {

  @Test
  public void unMiembroSeVinculaConUnSector() {
    Organizacion onu = new Organizacion("texto1", TipoOrganizacion.INSTITUCION, "texto2", "texto3");
    Sector compras = new Sector("Compras", new ArrayList<>());
    onu.incorporarSector(compras);
    Miembro jorgito = generarMiembro("jorge", "Nitales", 42222222, TIpoDocumento.DNI);
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

  public Miembro generarMiembro(String nombre,
                                String apellido,
                                int documento,
                                TIpoDocumento unTipo) {
    MiembroTest testMiembro = new MiembroTest();
    return testMiembro.generarMiembro(nombre, apellido, documento, unTipo);
  }
}