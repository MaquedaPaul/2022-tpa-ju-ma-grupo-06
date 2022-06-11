
import exceptions.*;
import mediciones.LectorDeCSV;
import mediciones.RepoMediciones;
import org.junit.jupiter.api.Test;
import tipo.consumo.RepoTipoDeConsumo;
import tipo.consumo.TipoActividad;
import tipo.consumo.TipoAlcance;
import tipo.consumo.TipoConsumo;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class MedicionesTest {
  // El lector falla si la ruta no es valida
  @Test
  public void elLectorFallaSiLaRutaDelArchivoNoExiste() {
    assertThrows(FileNotFoundException.class, () -> new LectorDeCSV("hola no funciono"));
  }

  // si hay mas de 4 columnas en una fila tiene que romper
  @Test
  public void siLeiMasDeCuatroColumnasEnUnaFilaTengoQueDetenerme() throws FileNotFoundException {
    LectorDeCSV lector = new LectorDeCSV("src/main/java/mediciones/medicionConMuchosCampos.csv");
    assertThrows(SeLeyeronMasCamposDeLosEsperados.class, lector::leerMediciones);
  }

  // un tipo de consumo invalido debe detener el lector
  @Test
  public void SiLeoUnTipoDeConsumoDesconocidoTengoQueDetenerme() throws FileNotFoundException {
    LectorDeCSV lector = new LectorDeCSV("src/main/java/mediciones/medicionTipoConsumoDesconocido.csv");
    assertThrows(LaMedicionEsNegativa.class, lector::leerMediciones);
  }

  // un valor negativo debe detener el lector
  @Test
  public void siLeoUnValorNegativoTengoQueDetenerme() throws FileNotFoundException {
    LectorDeCSV lector = new LectorDeCSV("src/main/java/mediciones/medicionValorNegativo.csv");
    assertThrows(LaMedicionEsNegativa.class, lector::leerMediciones);
  }

  // un periodo invalido debe detener el lector
  @Test
  public void siLeoUnaPerioricidadInvalidoTengoQueDetenerme() throws FileNotFoundException {
    LectorDeCSV lector = new LectorDeCSV("src/main/java/mediciones/medicionPerioricidadInvalida.csv");
    assertThrows(LaPerioricidadLeidaNoEsValida.class, lector::leerMediciones);
  }

  // un periodo de imputacion con un formato distinto debe romper
  @Test
  public void siLeoUnPeriodoDeImputacionInvalidoTengoQueDetenerme() throws FileNotFoundException {
    LectorDeCSV lector = new LectorDeCSV("src/main/java/mediciones/medicionPeriodoImputInvalido.csv");
    assertThrows(ElPeriodoDeImputacionNoEsValido.class, lector::leerMediciones);
  }

  // si el periodo de imputacion no concuerda con la perioricidad debe fallar
  @Test
  public void siElPeriodoNoConcuerdaConLaPerioricidadTengoQueDetenerme() throws FileNotFoundException {
    LectorDeCSV lector = new LectorDeCSV("src/main/java/mediciones/medicionNoConcuerda.csv");
    assertThrows(ElPeriodoNoConcuerdaConLaPerioricidad.class, lector::leerMediciones);
  }
  // en un archivo correcto debe agregar todas las mediciones
  @Test
  public void puedoGuardarLasMedicionesQueEstenCorrectas() throws FileNotFoundException {
    agregarTiposDeConsumoDePrueba();
    LectorDeCSV lector = new LectorDeCSV("src/main/java/mediciones/medicionesCorrectas.csv");
    assertEquals(lector.getCantidadDeMediciones(), 0);
    assertDoesNotThrow(lector::leerMediciones);
    assertEquals(3,lector.getCantidadDeMediciones());
  }

  // puedo cargar mediciones aunque rompa
  @Test
  public void puedoCargarLasMedicionesQueEstenCorrectas() throws FileNotFoundException {
    agregarTiposDeConsumoDePrueba();
    assertEquals(RepoMediciones.getInstance().medicionesTotales(), 0);
    LectorDeCSV lector = new LectorDeCSV("src/main/java/mediciones/medicionesParcialmenteCorrectas.csv");
    assertThrows(ElTipoDeConsumoLeidoNoEsValido.class,lector::leerMediciones);
    assertEquals(lector.getCantidadDeMediciones(),3);
    lector.cargarMediciones();
    assertEquals(RepoMediciones.getInstance().medicionesTotales(),3);
  }

  private void agregarTiposDeConsumoDePrueba() {
    TipoConsumo gas = new TipoConsumo("Gas Natural", "cm3", TipoActividad.COMBUSTION_MOVIL, TipoAlcance.EMISION_DIRECTA);
    TipoConsumo nafta = new TipoConsumo("Nafta", "lts", TipoActividad.COMBUSTION_MOVIL, TipoAlcance.EMISION_DIRECTA);
    RepoTipoDeConsumo.getInstance().agregarNuevoTipoDeConsumo(gas);
    RepoTipoDeConsumo.getInstance().agregarNuevoTipoDeConsumo(nafta);
  }
}
