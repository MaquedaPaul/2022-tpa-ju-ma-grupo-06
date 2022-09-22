package mediciones;

import admin.config.GestorDeFechas;
import registrohc.ControlDeHC;

import java.time.LocalDate;
import java.time.YearMonth;

public class TareaProgramadaMedicionesMensuales {

  public static void main(String[] args) {

    YearMonth mesAnterior = GestorDeFechas.getInstance().getFechaActualEnFormatoMensual(LocalDate.now().minusDays(1));
    ControlDeHC controladorHc = new ControlDeHC(mesAnterior);
    controladorHc.persistirMedicionesDelMes();

  }
}
