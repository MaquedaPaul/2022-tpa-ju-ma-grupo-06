import exceptions.NoExisteElSectorVinculante;
import exceptions.NoSePudoAbrirElArchivo;
import mediciones.MedicionBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MedicionesTest {

  @Test
  public void SeCreaUnaMedicionPeroNoSeEncuentraElArchivo() {
    assertThrows(NoSePudoAbrirElArchivo.class, () ->  new MedicionBuilder("estaRutaNoExiste.txt"));

  }
}
