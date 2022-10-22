package lectorcsv;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class FormatoDeFechasTest {

  FormatoDeFechas formatoFechas;

  HashMap<TipoPerioricidad, String> formato = new HashMap<>();

  @Test
  public void unaFechaMesYAnioNoEsValidaEnFormatoSoloAnual() {
    formato.put(TipoPerioricidad.ANUAL, "([0-9]{4})");
    formatoFechas = new FormatoDeFechas(formato);
    Assertions.assertFalse(formatoFechas.tieneElFormatoValido("08/2011", TipoPerioricidad.ANUAL));
  }

  @Test
  public void mensualNoEsUnPeriodoValidoSiNoSeCargaEnElFormato() {
    formato.put(TipoPerioricidad.ANUAL, "([0-9]{4})");

    formatoFechas = new FormatoDeFechas(formato);
    Assertions.assertFalse(formatoFechas.esUnPeriodoValido(TipoPerioricidad.MENSUAL));

  }

  @Test
  public void unaFechaMensualSoloEncajaConUnFormatoMensual() {
    formato.put(TipoPerioricidad.ANUAL, "([0-9]{4})");
    formato.put(TipoPerioricidad.MENSUAL, "(([1-9]|10|11|12)/[0-9]{4})");

    formatoFechas = new FormatoDeFechas(formato);
    Assertions.assertFalse(formatoFechas.tieneElFormatoValido("12/2009", TipoPerioricidad.ANUAL));
    Assertions.assertTrue(formatoFechas.tieneElFormatoValido("12/2009", TipoPerioricidad.MENSUAL));
  }

  @Test
  public void unMesMayorA12EsInvalido() {
    formato.put(TipoPerioricidad.ANUAL, "([0-9]{4})");
    formato.put(TipoPerioricidad.MENSUAL, "(([1-9]|10|11|12)/[0-9]{4})");

    formatoFechas = new FormatoDeFechas(formato);
    Assertions.assertFalse(formatoFechas.tieneElFormatoValido("22/2009", TipoPerioricidad.MENSUAL));
  }
}
