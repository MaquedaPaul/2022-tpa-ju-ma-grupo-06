package mediciones.perioricidad;

import lombok.Getter;
import lombok.Setter;
import organizacion.periodo.Periodo;
import organizacion.periodo.PeriodoMensual;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embeddable;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.time.LocalDate;

@Setter
@Getter
@Embeddable
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PERIORICIDAD")
public abstract class Perioricidad {

  private LocalDate fecha;
  private double valor;

  public abstract boolean esDelPeriodo(Periodo periodo);

  public abstract double calcularHCEntre(PeriodoMensual inicio, PeriodoMensual fin);

  public abstract double calcularHC(Periodo periodo);

  public abstract boolean esUnIntervaloValido(PeriodoMensual inicio, PeriodoMensual fin);

}
