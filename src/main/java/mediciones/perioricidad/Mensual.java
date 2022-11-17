package mediciones.perioricidad;

import exceptions.LaFechaDeInicioDebeSerAnteriorALaFechaDeFin;
import lombok.Getter;
import lombok.Setter;
import organizacion.periodo.Periodo;
import organizacion.periodo.PeriodoMensual;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.time.LocalDate;
@Getter
@Setter
@Embeddable

public class Mensual extends Perioricidad {

  public Mensual(){

  }
  public Mensual(LocalDate fecha, double valor) {
    this.setFecha(fecha);
    this.setValor(valor);
  }

  public boolean esDelPeriodo(Periodo periodo) {
    return this.getYear() == periodo.getYear() && this.getMonth() == periodo.getMonth();
  }

  public double calcularHCEntre(PeriodoMensual inicio, PeriodoMensual fin) {
    if (inicio.esDespuesDe(fin.getFecha())) {
      throw new LaFechaDeInicioDebeSerAnteriorALaFechaDeFin();
    }
    return this.esUnIntervaloValido(inicio, fin) ? getValor() : 0;
  }

  public double calcularHC(Periodo periodo) {
    return this.getValor();
  }

  public boolean esUnIntervaloValido(PeriodoMensual inicio, PeriodoMensual fin) {
    return (inicio.esAntesDe(this.getFecha()) && fin.esDespuesDe(this.getFecha()));
  }

  public int getYear() {
    return this.getFecha().getYear();
  }

  public int getMonth() {
    return this.getFecha().getMonthValue();
  }

}
