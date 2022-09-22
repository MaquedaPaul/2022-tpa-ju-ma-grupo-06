import admin.FactorEmision;
import exceptions.*;
import global.Unidad;
import mediciones.LectorDeCsv;
import mediciones.Medicion;
import mediciones.Perioricidad;
import mediciones.RepoMediciones;
import org.junit.jupiter.api.Test;
import organizacion.Organizacion;
import organizacion.TipoOrganizacion;
import tipoconsumo.RepoTipoDeConsumo;
import tipoconsumo.TipoActividad;
import tipoconsumo.TipoAlcance;
import tipoconsumo.TipoConsumo;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


public class MedicionesTest {
  // El lector falla si la ruta no es valida

  Organizacion mockOrg = mock(Organizacion.class);

  Medicion medicion = new Medicion(mock(TipoConsumo.class),
      Perioricidad.ANUAL,
      20D, 2020,
      12
      ,
      mock(Organizacion.class));

  Medicion medicionAnual = new Medicion(mock(TipoConsumo.class),
      Perioricidad.ANUAL,
      20000D, 2020, 0
      ,
      mock(Organizacion.class));

  Medicion medicionMensual = new Medicion(mock(TipoConsumo.class),
      Perioricidad.ANUAL,
      232D, 2020, 12
      ,
      mock(Organizacion.class));


  @Test
  public void elLectorFallaSiLaRutaDelArchivoNoExiste() {
    assertThrows(NoSuchFileException.class, () -> new LectorDeCsv("hola no funciono", mockOrg));
  }

  // si hay mas de 4 columnas en una fila tiene que romper
  @Test
  public void siLeiMasDeCuatroColumnasEnUnaFilaTengoQueDetenerme() throws IOException {
    agregarTiposDeConsumoDePrueba();
    LectorDeCsv lector = new LectorDeCsv("src/main/java/mediciones/medicionConMuchosCampos.csv", mockOrg);
    assertThrows(NoSeLeyeronLosCamposEsperados.class, lector::leerMediciones);
  }

  // un tipo de consumo invalido debe detener el lector
  @Test
  public void siLeoUnTipoDeConsumoDesconocidoTengoQueDetenerme() throws IOException {
    agregarTiposDeConsumoDePrueba();
    LectorDeCsv lector = new LectorDeCsv("src/main/java/mediciones/medicionTipoConsumoDesconocido.csv", mockOrg);
    assertThrows(ElTipoDeConsumoLeidoNoEsValido.class, lector::leerMediciones);
  }

  // si los nombres de los campos no son los que se esperan debo detenerme

  @Test
  public void siLosCamposTienenNombreDistintosALosEsperadosDeboDetenerme() throws IOException {
    LectorDeCsv lector = new LectorDeCsv("src/main/java/mediciones/medicionesConColumnasRandom.csv", mockOrg);
    assertThrows(LaCabeceraNoTieneUnFormatoValido.class, lector::leerMediciones);
  }

  // un valor negativo debe detener el lector
  @Test
  public void siLeoUnValorNegativoTengoQueDetenerme() throws IOException {
    agregarTiposDeConsumoDePrueba();
    LectorDeCsv lector = new LectorDeCsv("src/main/java/mediciones/medicionValorNegativo.csv", mockOrg);
    assertThrows(LaMedicionEsNegativa.class, lector::leerMediciones);
  }

  // un periodo invalido debe detener el lector
  @Test
  public void siLeoUnaPerioricidadInvalidoTengoQueDetenerme() throws IOException {
    agregarTiposDeConsumoDePrueba();
    LectorDeCsv lector = new LectorDeCsv("src/main/java/mediciones/medicionPerioricidadInvalida.csv", mockOrg);
    assertThrows(LaPerioricidadLeidaNoEsValida.class, lector::leerMediciones);
  }

  // un periodo de imputacion con un formato distinto debe romper
  @Test
  public void siLeoUnPeriodoDeImputacionInvalidoTengoQueDetenerme() throws IOException {
    agregarTiposDeConsumoDePrueba();
    LectorDeCsv lector = new LectorDeCsv("src/main/java/mediciones/medicionPeriodoImputInvalido.csv", mockOrg);
    assertThrows(ElPeriodoNoConcuerdaConLaPerioricidad.class, lector::leerMediciones);
  }

  // si el periodo de imputacion no concuerda con la perioricidad debe fallar
  @Test
  public void siElPeriodoNoConcuerdaConLaPerioricidadTengoQueDetenerme() throws IOException {
    agregarTiposDeConsumoDePrueba();
    LectorDeCsv lector = new LectorDeCsv("src/main/java/mediciones/medicionNoConcuerda.csv", mockOrg);
    assertThrows(ElPeriodoNoConcuerdaConLaPerioricidad.class, lector::leerMediciones);
  }

  // en un archivo correcto debe agregar todas las mediciones
  @Test
  public void puedoGuardarLasMedicionesQueEstenCorrectas() throws IOException {
    agregarTiposDeConsumoDePrueba();
    LectorDeCsv lector = new LectorDeCsv("src/main/java/mediciones/medicionesCorrectas.csv", mockOrg);
    assertEquals(lector.getCantidadDeMediciones(), 0);
    assertDoesNotThrow(lector::leerMediciones);
    assertEquals(5, lector.getCantidadDeMediciones());
  }

  // puedo cargar mediciones aunque rompa
  @Test
  public void puedoCargarLasMedicionesQueEstenCorrectas() throws IOException {
    agregarTiposDeConsumoDePrueba();
    Organizacion onu = new Organizacion("texto1", TipoOrganizacion.INSTITUCION, "texto2", "texto3", new ArrayList<>());
    assertEquals(RepoMediciones.getInstance().medicionesTotales(), 0);
    LectorDeCsv lector = new LectorDeCsv("src/main/java/mediciones/medicionParcialmenteCorrecta.csv", onu);
    assertThrows(NoSeLeyeronLosCamposEsperados.class, lector::leerMediciones);
    assertEquals(lector.getCantidadDeMediciones(), 2);
    lector.cargarMediciones();
    assertEquals(RepoMediciones.getInstance().medicionesTotales(), 2);
  }

  @Test
  public void calcularHc() {
    Organizacion onu = new Organizacion("texto1", TipoOrganizacion.INSTITUCION, "texto2", "texto3", new ArrayList<>());
    FactorEmision nuevoFActor = new FactorEmision(300, Unidad.LTS);
    TipoConsumo nuevoConsumo = new TipoConsumo("text", Unidad.LTS, TipoActividad.COMBUSTION_MOVIL, TipoAlcance.EMISION_DIRECTA);
    nuevoConsumo.setFactorEmision(nuevoFActor);
    Medicion nuevaMedicion = new Medicion(nuevoConsumo, Perioricidad.ANUAL, 150, 2020, 0, onu);
    assertEquals(nuevaMedicion.calcularHc(), 450);
  }

  @Test
  public void unaMedicionDeDIC_2020EsDel2020() {

    assertTrue(medicion.esDelAnio(2020));
  }

  @Test
  public void unaMedicionDeDIC_2020EsDeDiciembre() {

    assertTrue(medicion.esDelMes(12));
  }

  @Test
  public void siConsultoLaMedicionMensualDeUnaMedicionAnualMeDevuelveUnDoceavoDelValor() {

    assertEquals(medicionAnual.getValor() / 12, medicionAnual.getValorSegun(Perioricidad.MENSUAL));
  }

  @Test
  public void siConsultoLaMedicionMensualesAnualDeUnaMedicionMensualMeDevuelveElValorMensual() {

    assertEquals(medicionMensual.getValor(), medicionMensual.getValorSegun(Perioricidad.ANUAL));
  }


  private void agregarTiposDeConsumoDePrueba() {
    TipoConsumo gas = new TipoConsumo("Gas Natural", Unidad.CM3, TipoActividad.COMBUSTION_MOVIL, TipoAlcance.EMISION_DIRECTA);
    TipoConsumo nafta = new TipoConsumo("Nafta", Unidad.LTS, TipoActividad.COMBUSTION_MOVIL, TipoAlcance.EMISION_DIRECTA);
    RepoTipoDeConsumo.getInstance().agregarNuevoTipoDeConsumo(gas);
    RepoTipoDeConsumo.getInstance().agregarNuevoTipoDeConsumo(nafta);
  }
}
