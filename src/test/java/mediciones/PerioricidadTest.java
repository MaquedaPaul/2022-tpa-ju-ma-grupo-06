package mediciones;

import mediciones.perioricidad.Anual;
import mediciones.perioricidad.Mensual;
import mediciones.perioricidad.Perioricidad;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import organizacion.periodo.Periodo;
import organizacion.periodo.PeriodoAnual;
import organizacion.periodo.PeriodoMensual;

import java.time.LocalDate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PerioricidadTest {

  LocalDate fecha = LocalDate.of(2020, 8, 1);

  Periodo periodo = mock(Periodo.class);
  PeriodoMensual abr2020 = new PeriodoMensual(LocalDate.of(2020, 4, 3));
  PeriodoMensual sep2020 = new PeriodoMensual(LocalDate.of(2020, 9, 3));
  PeriodoMensual abr2021 = new PeriodoMensual(LocalDate.of(2021, 4, 3));
  PeriodoMensual sep2021 = new PeriodoMensual(LocalDate.of(2021, 9, 3));

  Periodo periodoMensual = new PeriodoMensual(fecha);
  Periodo periodoAnual = new PeriodoAnual(fecha);

  Perioricidad perioricidadAnual = new Anual(fecha, 1200D);
  Perioricidad perioricidadMensual = new Mensual(fecha, 200D);

  @Test
  public void unaPerioricidadEsCompatibleConUnPeriodoSiLaConcuerdaEnMesYAnio() {

    when(periodo.getMonth()).thenReturn(8);
    when(periodo.getYear()).thenReturn(2020);
    Assertions.assertTrue(perioricidadMensual.esDelPeriodo(periodo));
    Assertions.assertTrue(perioricidadAnual.esDelPeriodo(periodo));
  }

  @Test
  public void elHCEntreDosPeriodosMensualesDeUnaPerioricidadMensualEsSimplementeSuValor() {
    Assertions.assertEquals(200D, perioricidadMensual.calcularHCEntre(abr2020, sep2020));
  }

  @Test
  public void elHCEntreDosPeriodosMensualesQueNoConcuerdanConElAnioDeUnaPerioricidadAnualEsCero() {

    Assertions.assertEquals(0, perioricidadAnual.calcularHCEntre(abr2021, sep2021));
  }

  @Test
  public void elHCEntreDosPeriodosMensualesDondeElFinalNoConcuerdaConElAnioDeUnaPerioricidadAnualEsElValorDeLosMesesQueSiConcueden() {

    PeriodoMensual dic2020 = new PeriodoMensual(LocalDate.of(2020, 12, 1));
    Assertions.assertEquals(perioricidadAnual.calcularHCEntre(abr2020, dic2020), perioricidadAnual.calcularHCEntre(abr2020, abr2021));
  }

  @Test
  public void elHCEntreDosPeriodosMensualesDondeElInicioNoConcuerdaConElAnioDeUnaPerioricidadAnualEsElValorDeLosMesesQueSiConcueden() {

    PeriodoMensual ago2019 = new PeriodoMensual(LocalDate.of(2019, 8, 1));
    PeriodoMensual ene2020 = new PeriodoMensual(LocalDate.of(2020, 1, 1));

    Assertions.assertEquals(perioricidadAnual.calcularHCEntre(ago2019, abr2020), perioricidadAnual.calcularHCEntre(ene2020, abr2020));
  }

  @Test
  public void elHCAnualDeUnaPerioricidadMensualEsLaMismaQueSiSeConsultaseMensual() {

    Assertions.assertEquals(perioricidadMensual.calcularHC(periodoMensual), perioricidadMensual.calcularHC(periodoAnual));
  }

  @Test
  public void elHCMensualDeUnaPerioricidadAnualEsUnaDoceavaParteDelValor() {

    Assertions.assertEquals(100D, perioricidadAnual.calcularHC(periodoMensual));
  }

  @Test
  public void unIntervaloValidoEsAquelQueIncluyaALaFechaDeLaPerioricidad() {

    LocalDate unMesMenos = fecha.minusMonths(1);
    LocalDate unAnioMas = fecha.plusYears(1);
    PeriodoMensual per2 = new PeriodoMensual(unAnioMas);
    PeriodoMensual per1 = new PeriodoMensual(unMesMenos);

    Assertions.assertTrue(perioricidadAnual.esUnIntervaloValido(per1, per2));
    Assertions.assertTrue(perioricidadMensual.esUnIntervaloValido(per1, per2));
    Assertions.assertFalse(perioricidadAnual.esUnIntervaloValido(per2, per1));
    Assertions.assertFalse(perioricidadMensual.esUnIntervaloValido(per2, per1));

  }

}
