package mediciones.perioricidad;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.hibernate.annotations.ColumnDefault;
import organizacion.periodo.Periodo;
import organizacion.periodo.PeriodoMensual;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "perioricidad")
@DiscriminatorColumn(name = "perioricidad")
public abstract class Perioricidad {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "fecha")
  private LocalDate fecha;

  private double valor;

  public abstract boolean esDelPeriodo(Periodo periodo);

  public abstract double calcularHCEntre(PeriodoMensual inicio, PeriodoMensual fin);

  public abstract double calcularHC(Periodo periodo);

  public abstract boolean esUnIntervaloValido(PeriodoMensual inicio, PeriodoMensual fin);

}
