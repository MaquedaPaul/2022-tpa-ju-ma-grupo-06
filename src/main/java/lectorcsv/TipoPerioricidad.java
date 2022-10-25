package lectorcsv;

import mediciones.perioricidad.Anual;
import mediciones.perioricidad.Mensual;
import mediciones.perioricidad.Perioricidad;

import java.time.LocalDate;

public enum TipoPerioricidad {
  ANUAL() {
    @Override
    public Perioricidad getPerioricidad(LocalDate fecha, double valor) {
      return new Anual(fecha, valor);
    }

  },

  MENSUAL() {
    @Override
    public Perioricidad getPerioricidad(LocalDate fecha, double valor) {
      return new Mensual(fecha, valor);
    }
  };

  public abstract Perioricidad getPerioricidad(LocalDate fecha, double valor);
}
