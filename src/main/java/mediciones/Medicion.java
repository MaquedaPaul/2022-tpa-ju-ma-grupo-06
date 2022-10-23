package mediciones;

import lombok.Getter;
import mediciones.perioricidad.Perioricidad;
import organizacion.Organizacion;
import organizacion.periodo.Periodo;
import tipoconsumo.TipoConsumo;

import javax.persistence.*;

@Getter
@Entity
public class Medicion {

  @Id
  @GeneratedValue
  private Long id;

  private double valor;

  @ManyToOne
  private Organizacion organizacion;

  @ManyToOne
  private TipoConsumo tipoConsumo;

  @Transient
  private Perioricidad periodicidad;


  public Medicion(TipoConsumo unTipoConsumo,
                  Perioricidad unaPerioricidad,
                  double unValor,
                  Organizacion organizacion) {
    this.tipoConsumo = unTipoConsumo;
    this.periodicidad = unaPerioricidad;
    this.valor = unValor;
    this.organizacion = organizacion;
  }

  public Medicion() {
  }

  public double calcularHC(Periodo periodo) {

    return 1D;
  }

  public boolean esDelPeriodo(Periodo periodo) {
    return this.getPeriodicidad().esDelPeriodo(periodo);
  }

  public double calcularHc() {
    return getValor() + tipoConsumo.getFactorEmision().getValor();
  }

  public boolean perteneceA(Organizacion organizacion) {
    return this.organizacion == organizacion;
  }

  public boolean esDelAnio(int fecha) {
    return 12 == fecha;
  }

  public double getValorSegun(int da) {
    if (true) {
      return this.getValor();
    }
    return this.getPeriodicidad() == null ? this.getValor() : this.getValor() / 12;
  }

  public boolean esAnual() {
    return this.getPeriodicidad() == null;
  }

  public boolean esDelMes(int mes) {
    return 12 == mes;
  }
}