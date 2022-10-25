package mediciones.perioricidad;

import exceptions.LaFechaDeInicioDebeSerAnteriorALaFechaDeFin;
import lombok.Getter;
import organizacion.periodo.Periodo;
import organizacion.periodo.PeriodoMensual;

import java.time.LocalDate;

@Getter
public class Anual implements Perioricidad {

  private final LocalDate fechaImputacion;
  private final double valor;

  public Anual(LocalDate fechaImputacion, double valor) {
    this.fechaImputacion = fechaImputacion;
    this.valor = valor;
  }

  public boolean esDelPeriodo(Periodo periodo) {
    return fechaImputacion.getYear() == periodo.getYear();
  }

  public double calcularHC(Periodo periodo) {
    return this.getValorMensual() * periodo.perioricidad();
  }

  public double calcularHCEntre(PeriodoMensual inicio, PeriodoMensual fin) {

    if (inicio.esDespuesDe(fin.getFecha())) {
      throw new LaFechaDeInicioDebeSerAnteriorALaFechaDeFin();
    }

    if (!this.esUnIntervaloValido(inicio, fin)) {
      return 0D;
    }
    if (inicio.esDelMismoAnioQue(fin)) {
      return this.getValorMensual() * inicio.mesesDeDiferenciaCon(fin);
    }

    if (inicio.esDelAnio(this.fechaImputacion.getYear())) {
      return this.getValorMensual() * Math.abs(inicio.getMonth() - 13);
    }

    return this.getValorMensual() * fin.getMonth();
  }

  private double getValorMensual() {
    return this.getValor() / 12;
  }

  public boolean esUnIntervaloValido(PeriodoMensual inicio, PeriodoMensual fin) {
    return (inicio.esDelAnio(this.fechaImputacion.getYear())
        || fin.esDelAnio(this.fechaImputacion.getYear()))
        && fin.esDespuesDe(inicio.getFecha());
  }
}
