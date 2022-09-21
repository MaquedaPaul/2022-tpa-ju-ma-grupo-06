package mediciones;

import organizacion.Organizacion;
import tipoconsumo.TipoConsumo;

import javax.persistence.*;

@Entity
public class Medicion {

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
                  String periodoDeImputacion, Organizacion organizacion) {
    this.tipoConsumo = unTipoConsumo;
    this.periodicidad = unaPerioricidad;
    this.valor = unValor;
    this.periodoDeImputacion = periodoDeImputacion;
    this.organizacion = organizacion;
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

  public String getPeriodoDeImputacion() {
    return periodoDeImputacion;
  }

  public double calcularHc() {
    return getValor() + tipoConsumo.getFactorEmision().getValor();
  }

  public boolean perteneceA(Organizacion organizacion) {
    return this.organizacion == organizacion;
  }

  public boolean esDelAnio(String year) {
    int longitudString = getPeriodoDeImputacion().length();
    return getPeriodoDeImputacion().substring(longitudString - 4, longitudString).equals(year);
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

  public boolean esDelMes(String mes) {
    return this.getPeriodoDeImputacion().startsWith(mes);
  }
}