package lectorcsv;

import exceptions.*;
import global.Unidad;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import organizacion.Organizacion;
import tipoconsumo.TipoActividad;
import tipoconsumo.TipoAlcance;
import tipoconsumo.TipoConsumo;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LectorCSVTest {

  static Organizacion mockOrg;
  static FormatoDeFechas formato;
  static ValidadorDeCabeceras validador;
  static LectorDeCsv lector;

  List<String> campos = mock(ArrayList.class);

  static RepoTipoConsumo repoMock = mock(RepoTipoConsumo.class);

  TipoConsumo gas = new TipoConsumo("Gas Natural",
      Unidad.CM3, TipoActividad.COMBUSTION_MOVIL,
      TipoAlcance.EMISION_DIRECTA);
  TipoConsumo nafta = new TipoConsumo("Nafta",
      Unidad.LTS, TipoActividad.COMBUSTION_MOVIL,
      TipoAlcance.EMISION_DIRECTA);

  @BeforeAll
  static void inicializar() {
    mockOrg = mock(Organizacion.class);

    HashMap<TipoPerioricidad, String> formatos = new HashMap<>();
    formatos.put(TipoPerioricidad.ANUAL, "([0-9]{4})");
    formatos.put(TipoPerioricidad.MENSUAL, "(((0[1-9])|10|11|12)/[0-9]{4})");
    formato = new FormatoDeFechas(formatos);

    List<String> columnasEsperadas = new ArrayList<>();
    columnasEsperadas.add("tipoconsumo");
    columnasEsperadas.add("valor");
    columnasEsperadas.add("perioricidad");
    columnasEsperadas.add("periodo de imputacion");

    validador = new ValidadorDeCabeceras(columnasEsperadas);
    try {
      lector = new LectorDeCsv("src/test/java/lectorcsv/medicionesprueba/medicionesCorrectas.csv", mockOrg, formato, validador, repoMock);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  @Test
  public void elLectorFallaSiLaRutaDelArchivoNoExiste() {
    assertThrows(NoSuchFileException.class,
        () -> new LectorDeCsv("hola no funciono", mockOrg, formato, validador, repoMock));
  }

  @Test
  public void siLeiMasColumnasDeLasEsperadasDeboFallar() {
    when(campos.size()).thenReturn(3);
    assertThrows(NoSeLeyeronLosCamposEsperados.class,
        () -> lector.tieneLaCantidadCorrectaDeColumnas(campos));
  }

  @Test
  public void siLeoUnTipoDeConsumoDesconocidoTengoQueDetenerme() {
    assertThrows(ElTipoDeConsumoLeidoNoEsValido.class, () -> lector.esUnTipoDeConsumoValido("NAFFFFFFFTAAAAA"));
  }

  @Test
  public void siLeoUnValorNegativoTengoQueDetenerme() {
    assertThrows(LaMedicionEsNegativa.class, () -> lector.elValorLeidoEsPositivo(-1));
  }

  @Test
  public void siLeoUnaPerioricidadInvalidoTengoQueDetenerme() {
    assertThrows(LaPerioricidadLeidaNoEsValida.class,
        () -> lector.esUnaPerioricidadValida("MENNNNNSUAL"));
  }

  @Test
  public void siLeoUnPeriodoDeImputacionInvalidoTengoQueDetenerme() {
    assertThrows(ElPeriodoNoConcuerdaConLaPerioricidad.class,
        () -> lector.tieneElFormatoValido("11/2000", TipoPerioricidad.ANUAL));
  }

  @Test
  public void puedoGuardarLasMedicionesQueEstenCorrectas() {
    when(repoMock.existeElTipoDeConsumo("Gas Natural")).thenReturn(true);
    when(repoMock.existeElTipoDeConsumo("Nafta")).thenReturn(true);
    when(repoMock.getTipoConsumo("Gas Natural")).thenReturn(gas);
    when(repoMock.getTipoConsumo("Nafta")).thenReturn(nafta);
    Assertions.assertEquals(lector.getCantidadDeMediciones(), 0);
    assertDoesNotThrow(lector::leerMediciones);
    Assertions.assertEquals(5, lector.getCantidadDeMediciones());
  }

}
