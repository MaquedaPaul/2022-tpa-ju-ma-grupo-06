import static java.util.Objects.requireNonNull;

import java.util.List;



public class Miembro {
  String nombre;
  String apellido;
  TIpoDocumento tipoDocumento;
  int numeroDocumento;
  List<Organizacion> organizaciones;
  List<Trayecto> trayectos;

  public Miembro(
      String unNombre,
      String unApellido,
      TIpoDocumento unTipoDocumento,
      int unNumeroDocumento,
      List<Organizacion> unasOrganizaciones,
      List<Trayecto> unosTrayectos) {
    nombre = unNombre;
    apellido = unApellido;
    tipoDocumento = unTipoDocumento;
    numeroDocumento = unNumeroDocumento;
    organizaciones = unasOrganizaciones;
    trayectos = unosTrayectos;
  }

  void registrarTrayecto(Trayecto unTrayecto) {
    requireNonNull(unTrayecto);
    trayectos.add(unTrayecto);
  }

  void solicitarVinculacion(Organizacion unaOrganizacion, String nombreSector) {
    unaOrganizacion.procesarPedidoVinculacion(nombreSector, this);
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

  public List<Organizacion> getOrganizaciones() {
    return organizaciones;
  }

  public TIpoDocumento getTipoDocumento() {
    return tipoDocumento;
  }
}
