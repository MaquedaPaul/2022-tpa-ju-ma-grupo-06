package organizacion;

import admin.config.GestorDeFechas;
import lombok.Getter;
import transporte.Trayecto;

import javax.persistence.*;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Entity
@Getter
public class Miembro {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "miembro_id")
  private Long id;

  String nombre;
  String apellido;
  TipoDocumento tipoDocumento;
  int numeroDocumento;

  @Transient
  List<Trayecto> trayectos;


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

  public Miembro() {

  }

  public void registrarTrayecto(Trayecto unTrayecto) {
    requireNonNull(unTrayecto);
    trayectos.add(unTrayecto);
  }

  public void solicitarVinculacion(Organizacion unaOrganizacion, Solicitud unaSolicitud) {
    unaOrganizacion.recibirSolicitud(unaSolicitud);
  }


  public double calcularHCMensual() {
    return GestorDeFechas.getInstance().getDiasDeTrabajo() * this.calcularHCTrayectos();
  }


  public double calcularHCTrayectos() {
    return getTrayectos().stream().mapToDouble(Trayecto::calcularHC).sum();
  }
}
