import exceptions.NoExisteElSectorVinculante;
import org.junit.jupiter.api.Test;
import organizacion.*;
import organizacion.periodo.Anual;
import organizacion.periodo.Periodo;
import transporte.Trayecto;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

  @Test
  public void juanGenera2000HCPorMes() {
    Miembro juan = generarMiembro("juan", "juan", 2222, TipoDocumento.DNI);

    Trayecto trayecto1 = mock(Trayecto.class);
    Trayecto trayecto2 = mock(Trayecto.class);

    when(trayecto1.calcularHC()).thenReturn(40D);
    when(trayecto2.calcularHC()).thenReturn(60D);

    juan.registrarTrayecto(trayecto1);
    juan.registrarTrayecto(trayecto2);

    Periodo periodo = new Anual(LocalDate.of(2020, 3, 3));

    assertEquals(2000D, juan.calcularHCTotal(periodo));
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
