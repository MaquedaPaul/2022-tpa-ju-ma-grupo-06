package miembro;

import cuenta.MiembroCuenta;
import exceptions.ElTrayectoSeleccionadoNoPerteneceAEsteMiembro;
import exceptions.EsteTrayectoNoPuedeSerCompartido;
import lombok.Getter;
import lombok.Setter;
import organizacion.Organizacion;
import organizacion.Solicitud;
import organizacion.TipoDocumento;
import organizacion.periodo.Periodo;
import tipoconsumo.TipoConsumo;
import transporte.Trayecto;
import utils.ServiceLocator;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

@Getter
@Setter
@Entity
@Table(name = "MIEMBRO")
public class Miembro {

  @Id
  @GeneratedValue
  @Column(name = "ID_MIEMBRO")
  private Long id;

  @Column(name = "NOMBRE")
  String nombre;

  @Column(name = "APELLIDO")
  String apellido;

  @Enumerated
  @Column(name = "TIPO_DOCUMENTO")
  TipoDocumento tipoDocumento;

  @Column(name = "NRO_DOCUMENTO")
  int numeroDocumento;

  @OneToOne(fetch = FetchType.EAGER)
  MiembroCuenta cuenta;

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(name="MIEMBRO_POR_TRAYECTO",
      joinColumns = @JoinColumn(name="ID_MIEMBRO"),
      inverseJoinColumns = @JoinColumn(name="ID_TRAYECTO"))
  List<Trayecto> trayectos;

  protected Miembro() {
  }

  public Miembro(
      String unNombre,
      String unApellido,
      TipoDocumento unTipoDocumento,
      int unNumeroDocumento,
      List<Trayecto> unosTrayectos) {
    this.nombre = unNombre;
    this.apellido = unApellido;
    this.tipoDocumento = unTipoDocumento;
    this.numeroDocumento = unNumeroDocumento;
    this.trayectos = unosTrayectos;
  }

  public void registrarTrayecto(Trayecto unTrayecto) {
    requireNonNull(unTrayecto);
    trayectos.add(unTrayecto);
  }

  public void solicitarVinculacion(Organizacion unaOrganizacion, Solicitud unaSolicitud) {
    unaOrganizacion.recibirSolicitud(unaSolicitud);
  }

  public double calcularHCTotal(Periodo periodo) {
    return this.getDiasDeTrabajo() * this.calcularHCTrayectos() * periodo.perioricidad();
  }

  public int getDiasDeTrabajo() {
    return ServiceLocator.getInstance().getDiasDeTrabajo();
  }

  public double calcularHCTrayectos() {
    if(getTrayectos().isEmpty()){
      return 0;
    }
    return getTrayectos().stream().mapToDouble(Trayecto::calcularHC).sum();
  }

  public void compartirTrayectoCon(Miembro otro, Trayecto trayecto) {
    if (!this.trayectos.contains(trayecto)) {
      throw new ElTrayectoSeleccionadoNoPerteneceAEsteMiembro();
    }

    if (!trayecto.sePuedeCompartir()) {
      throw new EsteTrayectoNoPuedeSerCompartido();
    }

    otro.registrarTrayecto(trayecto);
  }

  public void setCuenta(MiembroCuenta cuenta) {
    this.cuenta = cuenta;
  }

  public String getNombreYApellido() { return getNombre() + " " + getApellido();}

}
