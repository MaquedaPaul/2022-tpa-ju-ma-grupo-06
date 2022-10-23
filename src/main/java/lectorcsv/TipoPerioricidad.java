package lectorcsv;

import mediciones.perioricidad.Anual;
import mediciones.perioricidad.Mensual;
import mediciones.perioricidad.Perioricidad;

import java.time.LocalDate;

public enum TipoPerioricidad {
  ANUAL() {
    @Override
    public Perioricidad getPerioricidad(LocalDate fecha) {
      return new Anual(fecha);
    }

  },

  MENSUAL() {
    @Override
    public Perioricidad getPerioricidad(LocalDate fecha) {
      return new Mensual(fecha);
    }
  };

  public abstract Perioricidad getPerioricidad(LocalDate fecha);
}
