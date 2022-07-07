package organizacion;

import static java.util.Objects.requireNonNull;

import java.util.List;

import transporte.Trayecto;
import lombok.Getter;

@Getter
public class Miembro {
  String nombre;
  String apellido;
  TipoDocumento tipoDocumento;
  int numeroDocumento;
  List<Trayecto> trayectos;
  int cantidadDeDiasQueTrabaja;

  public Miembro(
      String unNombre,
      String unApellido,
      TipoDocumento unTipoDocumento,
      int unNumeroDocumento,
      List<Trayecto> unosTrayectos,
      int cantidadDeDiasQueTrabaja) {
    this.nombre = unNombre;
    this.apellido = unApellido;
    this.tipoDocumento = unTipoDocumento;
    this.numeroDocumento = unNumeroDocumento;
    this.trayectos = unosTrayectos;
    this.cantidadDeDiasQueTrabaja = cantidadDeDiasQueTrabaja;
  }

  public void registrarTrayecto(Trayecto unTrayecto) {
    requireNonNull(unTrayecto);
    trayectos.add(unTrayecto);
  }

  public void solicitarVinculacion(Organizacion unaOrganizacion, Solicitud unaSolicitud) {
    unaOrganizacion.recibirSolicitud(unaSolicitud);
  }


  public double calcularHCTotal() {
    return getCantidadDeDiasQueTrabaja() * this.calcularHCTrayectos();
  }


  public double calcularHCTrayectos() {
    return 1;
    //return getTrayectos().stream().mapToDouble(unTrayecto -> unTrayecto.calcularHC()).sum();
  }
}
