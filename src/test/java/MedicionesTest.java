import exceptions.ElTipoLeidoNoEsValido;
import exceptions.LaMedicionEsNegativa;
import exceptions.NoSePudoAbrirElArchivo;
import exceptions.NoSeReconoceLaPeriodicidad;
import mediciones.MedicionBuilder;
import org.junit.jupiter.api.Test;
import tipo.consumo.RepoTipoDeConsumo;
import tipo.consumo.TipoActividad;
import tipo.consumo.TipoAlcance;
import tipo.consumo.TipoConsumo;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MedicionesTest {

  @Test
  public void SeCreaUnaMedicionPeroNoSeEncuentraElArchivo() {
    assertThrows(NoSePudoAbrirElArchivo.class, () ->  new MedicionBuilder("estaRutaNoExiste.txt"));
  }

  @Test
  public void elValorLeidoEsNegativo() {
    TipoConsumo nuevoConsumo = new TipoConsumo("GasNatural","text",TipoActividad.COMBUSTION_MOVIL,TipoAlcance.EMISION_DIRECTA);
    RepoTipoDeConsumo repo = RepoTipoDeConsumo.getInstance();
    repo.agregarNuevoTipoDeConsumo(nuevoConsumo);
    MedicionBuilder nueva = new MedicionBuilder("src/main/java/mediciones/medicionNegativa.csv");
    assertThrows(LaMedicionEsNegativa.class, () ->  nueva.cargarMediciones());
  }

  @Test
  public void laPerioricidadNoEsCorrecta() {
    TipoConsumo nuevoConsumo = new TipoConsumo("GasNatural","text",TipoActividad.COMBUSTION_MOVIL,TipoAlcance.EMISION_DIRECTA);
    RepoTipoDeConsumo repo = RepoTipoDeConsumo.getInstance();
    repo.agregarNuevoTipoDeConsumo(nuevoConsumo);
    MedicionBuilder nueva = new MedicionBuilder("src/main/java/mediciones/noSeReconocePerioricidad.csv");
    assertThrows(NoSeReconoceLaPeriodicidad.class, () ->  nueva.cargarMediciones());
  }

  /*
  @Test
  public void elValorLeidoEsInconrrecto() {
    TipoConsumo nuevoConsumo = new TipoConsumo("GasNatural","text",TipoActividad.COMBUSTION_MOVIL,TipoAlcance.EMISION_DIRECTA);
    RepoTipoDeConsumo repo = RepoTipoDeConsumo.getInstance();
    repo.agregarNuevoTipoDeConsumo(nuevoConsumo);
    MedicionBuilder nueva = new MedicionBuilder("src/main/java/mediciones/tipoLeidoNoValido.csv");
    assertThrows(ElTipoLeidoNoEsValido.class, () ->  nueva.cargarMediciones());
  }

   */
}
