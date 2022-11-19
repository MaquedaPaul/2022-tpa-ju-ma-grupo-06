package mediciones;

import lombok.Getter;
import mediciones.perioricidad.Perioricidad;
import organizacion.Organizacion;
import organizacion.periodo.Periodo;
import organizacion.periodo.PeriodoMensual;
import tipoconsumo.TipoConsumo;

import javax.persistence.*;

@Getter
@Entity
public class MedicionAntigua {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  private Organizacion organizacion;

  @ManyToOne(fetch = FetchType.EAGER)
  private TipoConsumo tipoConsumo;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "ID_PERIORICIDAD")
  private Perioricidad periodicidad;


  public MedicionAntigua(TipoConsumo unTipoConsumo,
                         Perioricidad unaPerioricidad,
                         Organizacion organizacion) {
    this.tipoConsumo = unTipoConsumo;
    this.periodicidad = unaPerioricidad;
    this.organizacion = organizacion;
  }

  public MedicionAntigua() {
  }

  public double calcularHC(Periodo periodo) {
    return this.esDelPeriodo(periodo) ? this.getPeriodicidad().calcularHC(periodo) : 0D;
  }

  public double calcularHCEntre(PeriodoMensual inicio, PeriodoMensual fin) {
    return this.getPeriodicidad().calcularHCEntre(inicio, fin);
  }

  public boolean esDelPeriodo(Periodo periodo) {
    return this.getPeriodicidad().esDelPeriodo(periodo);
  }

}