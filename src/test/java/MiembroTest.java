import exceptions.NoSeAceptaVinculacion;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MiembroTest {

  @Test
  public void unMiembroNoPuedeVincularseAUnSectorQueNoPerteneceALaOrganizacion() {
    List<Organizacion> organizaciones = new ArrayList<>();
    Organizacion onu = new Organizacion("texto1", TipoOrganizacion.INSTITUCION, "texto2", "texto3");
    organizaciones.add(onu);
    Sector compras = new Sector("Compras",new ArrayList<>());

    MiembroBuilder nuevoMiembro = new MiembroBuilder();
    nuevoMiembro.especificarNombre("Jorge");
    nuevoMiembro.especificarApellido("Nitales");
    nuevoMiembro.especificarOrganizaciones(organizaciones);
    nuevoMiembro.especificarNumeroDocumento(42222222);
    nuevoMiembro.especificarTipoDocumento(TIpoDocumento.DNI);
    nuevoMiembro.especificarTrayectos(new ArrayList<>());
    Miembro jorgito = nuevoMiembro.construir();

    assertThrows(NoSeAceptaVinculacion.class,() -> jorgito.solicitarVinculacion(onu,compras));
  }

  @Test
  public void unMiembroPuedeVincularseAUnSectorQuePerteneceALaOrganizacion() {
    List<Organizacion> organizaciones = new ArrayList<>();
    Organizacion onu = new Organizacion("texto1", TipoOrganizacion.INSTITUCION, "texto2", "texto3");
    organizaciones.add(onu);
    onu.crearSector("Compras",new ArrayList<>());
    Sector compras = new Sector("Compras",new ArrayList<>());

    MiembroBuilder nuevoMiembro = new MiembroBuilder();
    nuevoMiembro.especificarNombre("Jorge");
    nuevoMiembro.especificarApellido("Nitales");
    nuevoMiembro.especificarOrganizaciones(organizaciones);
    nuevoMiembro.especificarNumeroDocumento(42222222);
    nuevoMiembro.especificarTipoDocumento(TIpoDocumento.DNI);
    nuevoMiembro.especificarTrayectos(new ArrayList<>());
    Miembro jorgito = nuevoMiembro.construir();

    assertThrows(NoSeAceptaVinculacion.class,() -> jorgito.solicitarVinculacion(onu,compras));
  }
}
