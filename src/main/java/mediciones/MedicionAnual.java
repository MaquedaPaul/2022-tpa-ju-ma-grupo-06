package mediciones;

import exceptions.LaFechaDeInicioDebeSerAnteriorALaFechaDeFin;
import lombok.Getter;
import organizacion.Organizacion;
import organizacion.periodo.Periodo;
import organizacion.periodo.PeriodoMensual;
import tipoconsumo.TipoConsumo;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Getter
public class MedicionAnual extends Medicion {

  private double valor;

  public MedicionAnual(Organizacion org, TipoConsumo tipoConsumo, LocalDate fecha, double valor) {
    this.setFecha(fecha);
    this.valor = valor;
    this.setOrganizacion(org);
    this.setTipoConsumo(tipoConsumo);
  }

  protected MedicionAnual() {

  }

  @Override
  public double calcularHC(Periodo periodo) {
    return this.valor * (periodo.perioricidad() / 12D);
  }

  @Override
  public double calcularHCEntre(PeriodoMensual inicio, PeriodoMensual fin) {
    if (inicio.esDespuesDe(fin.getFecha())) {
      throw new LaFechaDeInicioDebeSerAnteriorALaFechaDeFin();
    }

    if (!this.fueRegistradaEntre(inicio, fin)) {
      return 0D;
    }

    if (inicio.esDelMismoAnioQue(fin)) {
      return this.getValor() * inicio.mesesDeDiferenciaCon(fin);
    }

    if (inicio.esDelAnio(this.getFecha().getYear())) {
      return this.getValor() * Math.abs(inicio.getMonth() - 13);
    }

    return this.getValor() * fin.getMonth();
  }

  @Override
  public boolean esDelPeriodo(Periodo periodo) {
    return this.getYear() == periodo.getYear();
  }

  @Override
  public boolean fueRegistradaEntre(PeriodoMensual inicio, PeriodoMensual fin) {
    return (inicio.esDelAnio(this.getYear())
        || fin.esDelAnio(this.getYear()))
        && fin.esDespuesDe(inicio.getFecha());
  }

}
