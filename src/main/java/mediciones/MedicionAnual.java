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


  /**
   * Retorna el hc correspondiente a la medicion entre dos periodos
   * <ul>
   *   <li>Si el <strong>inicio</strong> es despues del <strong>fin</strong> rompe</li>
   *   <li>Si la medicion fue no registrada entre <strong>inicio</strong> y <strong>fin</strong> retorna 0</li>
   * </ul>
   * @param inicio PeriodoMensual
   * @param fin PeriodoMensual
   * @throws LaFechaDeInicioDebeSerAnteriorALaFechaDeFin si el <strong>inicio</strong> es despues del <strong>fin</strong>
   */
  @Override
  public double calcularHCEntre(PeriodoMensual inicio, PeriodoMensual fin) {
    if (inicio.esDespuesDe(fin.getFecha())) {
      throw new LaFechaDeInicioDebeSerAnteriorALaFechaDeFin();
    }

    if (!this.fueRegistradaEntre(inicio, fin)) {
      return 0D;
    }

    if (inicio.esDelMismoAnioQue(fin)) {
      return this.getValorMensual() * inicio.mesesDeDiferenciaCon(fin);
    }

    if (inicio.esDelAnio(this.getFecha().getYear())) {
      return this.getValorMensual() * Math.abs(inicio.getMonth() - 13);
    }

    return this.getValorMensual() * fin.getMonth();
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

  private double getValorMensual() {
    return this.getValor() / 12;
  }

}
