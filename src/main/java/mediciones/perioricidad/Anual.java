package mediciones.perioricidad;

import exceptions.LaFechaDeInicioDebeSerAnteriorALaFechaDeFin;
import organizacion.periodo.Periodo;
import organizacion.periodo.PeriodoMensual;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Anual extends Perioricidad {

  public Anual(LocalDate fechaImputacion, double valor) {
    this.setFecha(fechaImputacion);
    this.setValor(valor);
  }

  public Anual() {

  }

  public boolean esDelPeriodo(Periodo periodo) {
    return this.getYear() == periodo.getYear();
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

    if (inicio.esDelAnio(this.getYear())) {
      return this.getValorMensual() * Math.abs(inicio.getMonth() - 13);
    }

    return this.getValorMensual() * fin.getMonth();
  }

  private double getValorMensual() {
    return this.getValor() / 12;
  }

  @Override
  public boolean esUnIntervaloValido(PeriodoMensual inicio, PeriodoMensual fin) {
    return (inicio.esDelAnio(this.getYear())
        || fin.esDelAnio(this.getYear()))
        && fin.esDespuesDe(inicio.getFecha());
  }

  public int getYear() {
    return this.getFecha().getYear();
  }

}
