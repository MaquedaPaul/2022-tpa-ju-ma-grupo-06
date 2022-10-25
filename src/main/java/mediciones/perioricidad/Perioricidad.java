package mediciones.perioricidad;

import organizacion.periodo.Periodo;
import organizacion.periodo.PeriodoMensual;

public interface Perioricidad {

  boolean esDelPeriodo(Periodo periodo);

  double calcularHCEntre(PeriodoMensual inicio, PeriodoMensual fin);

  double calcularHC(Periodo periodo);

  boolean esUnIntervaloValido(PeriodoMensual inicio, PeriodoMensual fin);
}
