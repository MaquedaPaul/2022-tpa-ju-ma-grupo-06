package organizacion;

import admin.config.GestorDeFechas;
import lombok.Getter;
import tipoconsumo.TipoConsumo;
import transporte.Trayecto;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

@Getter
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

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(name="MIEMBRO_POR_TRAYECTO",
      joinColumns = @JoinColumn(name="ID_MIEMBRO"),
      inverseJoinColumns = @JoinColumn(name="ID_TRAYECTO"))
  List<Trayecto> trayectos;

  public Miembro() {
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


  public double calcularHCMensual() {
    return GestorDeFechas.getInstance().getDiasDeTrabajo() * this.calcularHCTrayectos();
  }


  public double calcularHCTrayectos() {
    return getTrayectos().stream().mapToDouble(Trayecto::calcularHC).sum();
  }

  public Stream<TipoConsumo> getTiposDeConsumoUsados() {
    return this.getTrayectos().stream()
        .map(Trayecto::getTiposDeConsumo)
        .flatMap(Stream::distinct);
  }
}
