package mediciones;

import mediciones.perioricidad.Anual;
import mediciones.perioricidad.Mensual;
import mediciones.perioricidad.Perioricidad;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import organizacion.Organizacion;
import organizacion.periodo.Periodo;
import organizacion.periodo.PeriodoAnual;
import organizacion.periodo.PeriodoMensual;
import tipoconsumo.TipoConsumo;

import java.time.LocalDate;

import static org.mockito.Mockito.mock;

public class MedicionesTest {

  TipoConsumo tconsumo = mock(TipoConsumo.class);
  LocalDate dic2020 = LocalDate.of(2020, 12, 1);
  Organizacion orgMock = mock(Organizacion.class);

  Perioricidad mensual = new Mensual(dic2020, 2000D);
  Perioricidad anual = new Anual(dic2020, 2000D);

  Periodo periodoAnual = new PeriodoAnual(dic2020);
  Periodo periodoMensual = new PeriodoMensual(dic2020);

  Medicion medicionMensual = new Medicion(tconsumo, mensual, orgMock);
  Medicion medicionAnual = new Medicion(tconsumo, anual, orgMock);
  Medicion medicionAnual2020 = new Medicion(tconsumo, anual, orgMock);

  @Test
  public void unaMedicionMensualAnteUnaConsultaAnualDevuelveSuValorNormal() {

    Assertions.assertEquals(2000D, medicionMensual.calcularHC(periodoAnual));
  }

  @Test
  public void unaMedicionAnualAnteUnaConsultaMensualDevuelveUnDoceavoDeSuValor() {

    Assertions.assertEquals((double) 2000 / 12, medicionAnual.calcularHC(periodoMensual));
  }


  @Test
  public void unaMedicionAnualEsDeUnPeriodoMensualSiEsteEsEnElMismoAnio() {

    Periodo enero2020 = new PeriodoMensual(LocalDate.of(2020, 1, 1));
    Assertions.assertTrue(medicionAnual2020.esDelPeriodo(enero2020));
  }

  @Test
  public void elHCEntreJUL2020yDIC2020DeLaMedicionAnualEsElConsumoEntreJULyDICIncluidoDIC() {

    PeriodoMensual jul2020 = new PeriodoMensual(LocalDate.of(2020, 7, 1));
    PeriodoMensual dic2020 = new PeriodoMensual(LocalDate.of(2020, 12, 1));

    Assertions.assertEquals(6 * ((double) 2000 / 12), medicionAnual2020.calcularHCEntre(jul2020, dic2020));

  }
}
