package organizacion;

import static java.util.Objects.requireNonNull;

import java.util.List;

import transporte.Tramo;
import transporte.Trayecto;

public class Miembro {
  String nombre;
  String apellido;
  TIpoDocumento tipoDocumento;
  int numeroDocumento;
  List<Trayecto> trayectos;
  int cantidadDiasQueTrabaja;

  public Miembro(
      String unNombre,
      String unApellido,
      TIpoDocumento unTipoDocumento,
      int unNumeroDocumento,
      List<Trayecto> unosTrayectos) {
    nombre = unNombre;
    apellido = unApellido;
    tipoDocumento = unTipoDocumento;
    numeroDocumento = unNumeroDocumento;
    trayectos = unosTrayectos;
  }

  void registrarTrayecto(Trayecto unTrayecto) {
    requireNonNull(unTrayecto);
    trayectos.add(unTrayecto);
  }

  public void solicitarVinculacion(Organizacion unaOrganizacion, Solicitud unaSolicitud) {
    unaOrganizacion.recibirSolicitud(unaSolicitud);
  }

  public String getApellido() {
    return apellido;
  }

  public String getNombre() {
    return nombre;
  }

  public int getNumeroDocumento() {
    return numeroDocumento;
  }

  public TIpoDocumento getTipoDocumento() {
    return tipoDocumento;
  }

  public double calcularHcTotal(){
    return trayectos.stream().mapToDouble(Trayecto::calcularHc).sum() * cantidadDiasQueTrabaja;
  }
}
