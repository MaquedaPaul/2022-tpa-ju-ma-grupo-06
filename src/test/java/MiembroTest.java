import org.junit.jupiter.api.Test;

public class MiembroTest {

  MiembroBuilder registroDeMiembros = new MiembroBuilder();
  registroDeMiembros.especificarNombre("Jorge");
  registroDeMiembros.especificarApellido("Nitales");

  Miembro jorgito = registroDeMiembros.construir();

  @Test
  public void unMiembroPuedeVincularseAUnSectorDeLaOrganizacion() {

  }


}
