package mediciones;

import organizacion.Organizacion;
import tipoconsumo.TipoConsumo;

import javax.persistence.*;

@Entity
public class Medicion {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  TipoConsumo tipoConsumo;

  @Enumerated
  Perioricidad periodicidad;
  private double valor;
  private int anio;
  private int mes;

  @ManyToOne
  private Organizacion organizacion;

  @Id
  @GeneratedValue
  private long id;

  @ManyToOne
  private TipoConsumo tipoConsumo;

  @Enumerated
  private Perioricidad periodicidad;

  @Column
  private double valor;

  @Column
  private String periodoDeImputacion;

  @Transient
  private Organizacion organizacion;

  public Medicion() {
  }

  public Medicion(TipoConsumo unTipoConsumo,
                  Perioricidad unaPerioricidad,
                  double unValor,
                  int anio,
                  int mes,
                  Organizacion organizacion) {
    this.tipoConsumo = unTipoConsumo;
    this.periodicidad = unaPerioricidad;
    this.valor = unValor;
    this.anio = anio;
    this.mes = mes;
    this.organizacion = organizacion;
  }

  public Medicion() {

  }

  public TipoConsumo getTipoConsumo() {
    return tipoConsumo;
  }

  public Perioricidad getPeriodicidad() {
    return periodicidad;
  }

  public double getValor() {
    return valor;
  }

  public double calcularHc() {
    return getValor() + tipoConsumo.getFactorEmision().getValor();
  }

  public boolean perteneceA(Organizacion organizacion) {
    return this.organizacion == organizacion;
  }

  public boolean esDelAnio(int fecha) {
    return this.anio == fecha;
  }

  public double getValorSegun(Perioricidad periodo) {
    if (periodo == Perioricidad.ANUAL) {
      return this.getValor();
    }
    return this.getPeriodicidad() == Perioricidad.MENSUAL ? this.getValor() : this.getValor() / 12;
  }

  public boolean esAnual() {
    return this.getPeriodicidad() == Perioricidad.ANUAL;
  }

  public boolean esDelMes(int mes) {
    return this.mes == mes;
  }
}