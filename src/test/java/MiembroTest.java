import exceptions.NoSeAceptaVinculacion;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MiembroTest {

  @Test
  public void unMiembroNoPuedeVincularseAUnSectorQueNoPerteneceALaOrganizacion() {
    List<Organizacion> organizaciones = new ArrayList<>();
    Organizacion onu = new Organizacion("texto1", TipoOrganizacion.INSTITUCION, "texto2", "texto3");
    organizaciones.add(onu);

    MiembroBuilder nuevoMiembro = new MiembroBuilder();
    nuevoMiembro.especificarNombre("Jorge");
    nuevoMiembro.especificarApellido("Nitales");
    nuevoMiembro.especificarOrganizaciones(organizaciones);
    nuevoMiembro.especificarNumeroDocumento(42222222);
    nuevoMiembro.especificarTipoDocumento(TIpoDocumento.DNI);
    nuevoMiembro.especificarTrayectos(new ArrayList<>());
    Miembro jorgito = nuevoMiembro.construir();

    assertThrows(NoSeAceptaVinculacion.class,() -> jorgito.solicitarVinculacion(onu,"Compras"));
  }

  @Test
  public void unMiembroPuedeVincularseAUnSectorQuePerteneceALaOrganizacion() {
    List<Organizacion> organizaciones = new ArrayList<>();
    Organizacion onu = new Organizacion("texto1", TipoOrganizacion.INSTITUCION, "texto2", "texto3");
    organizaciones.add(onu);
    onu.crearSector("Compras",new ArrayList<>());

    MiembroBuilder nuevoMiembro = new MiembroBuilder();
    nuevoMiembro.especificarNombre("Jorge");
    nuevoMiembro.especificarApellido("Nitales");
    nuevoMiembro.especificarOrganizaciones(organizaciones);
    nuevoMiembro.especificarNumeroDocumento(42222222);
    nuevoMiembro.especificarTipoDocumento(TIpoDocumento.DNI);
    nuevoMiembro.especificarTrayectos(new ArrayList<>());
    Miembro jorgito = nuevoMiembro.construir();

    jorgito.solicitarVinculacion(onu,"Compras");
    assertTrue(onu.sectores.stream().filter(sector -> sector.getNombre().equals("Compras")).collect(Collectors.toList()).get(0).getMiembros().contains(jorgito));
  }
}
