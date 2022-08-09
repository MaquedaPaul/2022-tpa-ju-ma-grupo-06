import exceptions.NoExisteElSectorVinculante;
import org.junit.jupiter.api.Test;
import organizacion.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MiembroTest {

  @Test
  public void unMiembroNoPuedeVincularseAUnSectorQueNoPerteneceALaOrganizacion() {
    Organizacion onu = new Organizacion("texto1", TipoOrganizacion.INSTITUCION, "texto2", "texto3", new ArrayList<>());
    Miembro jorgito = generarMiembro("jorge", "Nitales", 42222222, TipoDocumento.DNI);
    Sector ventas = new Sector("Ventas", new ArrayList<>());
    onu.incorporarSector(ventas);
    Sector compras = new Sector("Compras", new ArrayList<>());
    Solicitud nuevaSolicitud = new Solicitud(jorgito, compras);
    assertThrows(NoExisteElSectorVinculante.class, () -> jorgito.solicitarVinculacion(onu, nuevaSolicitud));
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
