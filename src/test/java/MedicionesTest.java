import exceptions.ElPeriodoNoConcuerdaConLaPerioricidad;
import exceptions.ElTipoDeConsumoLeidoNoEsValido;
import exceptions.LaMedicionEsNegativa;
import exceptions.LaPerioricidadLeidaNoEsValida;
import exceptions.NoSeLeyeronLosCamposEsperados;
import mediciones.LectorDeCsv;
import mediciones.RepoMediciones;
import org.junit.jupiter.api.Test;
import tipoconsumo.RepoTipoDeConsumo;
import tipoconsumo.TipoActividad;
import tipoconsumo.TipoAlcance;
import tipoconsumo.TipoConsumo;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class MedicionesTest {
  // El lector falla si la ruta no es valida
  @Test
  public void elLectorFallaSiLaRutaDelArchivoNoExiste() {
    assertThrows(FileNotFoundException.class, () -> new LectorDeCsv("hola no funciono"));
  }

  // si hay mas de 4 columnas en una fila tiene que romper
  @Test
  public void siLeiMasDeCuatroColumnasEnUnaFilaTengoQueDetenerme() throws FileNotFoundException {
    agregarTiposDeConsumoDePrueba();
    LectorDeCsv lector = new LectorDeCsv("src/main/java/mediciones/medicionConMuchosCampos.csv");
    assertThrows(NoSeLeyeronLosCamposEsperados.class, lector::leerMediciones);

  }

  // un tipo de consumo invalido debe detener el lector
  @Test
  public void SiLeoUnTipoDeConsumoDesconocidoTengoQueDetenerme() throws FileNotFoundException {
    agregarTiposDeConsumoDePrueba();
    LectorDeCsv lector = new LectorDeCsv("src/main/java/mediciones/medicionTipoConsumoDesconocido.csv");
    assertThrows(ElTipoDeConsumoLeidoNoEsValido.class, lector::leerMediciones);
  }

  // un valor negativo debe detener el lector
  @Test
  public void siLeoUnValorNegativoTengoQueDetenerme() throws FileNotFoundException {
    agregarTiposDeConsumoDePrueba();
    LectorDeCsv lector = new LectorDeCsv("src/main/java/mediciones/medicionValorNegativo.csv");
    assertThrows(LaMedicionEsNegativa.class, lector::leerMediciones);
  }

  // un periodo invalido debe detener el lector
  @Test
  public void siLeoUnaPerioricidadInvalidoTengoQueDetenerme() throws FileNotFoundException {
    agregarTiposDeConsumoDePrueba();
    LectorDeCsv lector = new LectorDeCsv("src/main/java/mediciones/medicionPerioricidadInvalida.csv");
    assertThrows(LaPerioricidadLeidaNoEsValida.class, lector::leerMediciones);
  }

  // un periodo de imputacion con un formato distinto debe romper
  @Test
  public void siLeoUnPeriodoDeImputacionInvalidoTengoQueDetenerme() throws FileNotFoundException {
    agregarTiposDeConsumoDePrueba();
    LectorDeCsv lector = new LectorDeCsv("src/main/java/mediciones/medicionPeriodoImputInvalido.csv");
    assertThrows(ElPeriodoNoConcuerdaConLaPerioricidad.class, lector::leerMediciones);
  }

  // si el periodo de imputacion no concuerda con la perioricidad debe fallar
  @Test
  public void siElPeriodoNoConcuerdaConLaPerioricidadTengoQueDetenerme() throws FileNotFoundException {
    agregarTiposDeConsumoDePrueba();
    LectorDeCsv lector = new LectorDeCsv("src/main/java/mediciones/medicionNoConcuerda.csv");
    assertThrows(ElPeriodoNoConcuerdaConLaPerioricidad.class, lector::leerMediciones);
  }
  // en un archivo correcto debe agregar todas las mediciones
  @Test
  public void puedoGuardarLasMedicionesQueEstenCorrectas() throws FileNotFoundException {
    agregarTiposDeConsumoDePrueba();
    LectorDeCsv lector = new LectorDeCsv("src/main/java/mediciones/medicionesCorrectas.csv");
    assertEquals(lector.getCantidadDeMediciones(), 0);
    assertDoesNotThrow(lector::leerMediciones);
    assertEquals(5,lector.getCantidadDeMediciones());
  }

  // puedo cargar mediciones aunque rompa
  @Test
  public void puedoCargarLasMedicionesQueEstenCorrectas() throws FileNotFoundException {
    agregarTiposDeConsumoDePrueba();
    assertEquals(RepoMediciones.getInstance().medicionesTotales(), 0);
    LectorDeCsv lector = new LectorDeCsv("src/main/java/mediciones/medicionParcialmenteCorrecta.csv");
    assertThrows(NoSeLeyeronLosCamposEsperados.class,lector::leerMediciones);
    assertEquals(lector.getCantidadDeMediciones(),2);
    lector.cargarMediciones();
    assertEquals(RepoMediciones.getInstance().medicionesTotales(),2);
  }

  private void agregarTiposDeConsumoDePrueba() {
    TipoConsumo gas = new TipoConsumo("Gas Natural", "cm3", TipoActividad.COMBUSTION_MOVIL, TipoAlcance.EMISION_DIRECTA);
    TipoConsumo nafta = new TipoConsumo("Nafta", "lts", TipoActividad.COMBUSTION_MOVIL, TipoAlcance.EMISION_DIRECTA);
    RepoTipoDeConsumo.getInstance().agregarNuevoTipoDeConsumo(gas);
    RepoTipoDeConsumo.getInstance().agregarNuevoTipoDeConsumo(nafta);
  }
}
