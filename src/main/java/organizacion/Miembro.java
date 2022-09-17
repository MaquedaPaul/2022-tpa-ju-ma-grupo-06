package organizacion;

import admin.config.ValoresGlobales;
import lombok.Getter;
import transporte.Trayecto;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Getter
public class Miembro {
  String nombre;
  String apellido;
  TipoDocumento tipoDocumento;
  int numeroDocumento;
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

  public void registrarTrayecto(Trayecto unTrayecto) {
    requireNonNull(unTrayecto);
    trayectos.add(unTrayecto);
  }

  public void solicitarVinculacion(Organizacion unaOrganizacion, Solicitud unaSolicitud) {
    unaOrganizacion.recibirSolicitud(unaSolicitud);
  }


  public double calcularHCMensual() {
    return ValoresGlobales.getInstance().getDiasDeTrabajo() * this.calcularHCTrayectos();
  }


  public double calcularHCTrayectos() {
    return getTrayectos().stream().mapToDouble(Trayecto::calcularHC).sum();
  }
}
