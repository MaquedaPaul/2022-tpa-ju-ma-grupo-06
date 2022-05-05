import java.util.List;

import static java.util.Objects.requireNonNull;

class MiembroBuilder {
  String nombre;
  String apellido;
  TIpoDocumento tipoDocumento;
  int numeroDocumento;
  List<Organizacion> organizaciones;
  List<Trayecto> trayectos;

  Miembro construir() {
    this.validar();
    return new Miembro(nombre, apellido, tipoDocumento, numeroDocumento, organizaciones, trayectos);
  }

  void especificarNombre(String unNombre) {
    requireNonNull(unNombre);
    nombre = unNombre;
  }

  void especificarApellido(String unApellido) {
    requireNonNull(unApellido);
    apellido = unApellido;
  }

  void especificarTipoDocumento(TIpoDocumento unTipoDocumento) {
    requireNonNull(unTipoDocumento);
    tipoDocumento = unTipoDocumento;
  }

  void especificarNumeroDocumento(int unNumeroDocumento) {
    numeroDocumento = unNumeroDocumento;
  }

  void especificarOrganizaciones(List<Organizacion> unasOrganizaciones) {
    requireNonNull(unasOrganizaciones);
    organizaciones = unasOrganizaciones;
  }

  void especificarTrayectos(List<Trayecto> unosTrayectos) {
    //podría ser que no se desplace porque es virtual
    trayectos = unosTrayectos;
  }

  void validar() {
    requireNonNull(nombre);
    requireNonNull(apellido);
    requireNonNull(tipoDocumento);
    requireNonNull(organizaciones);
  }

}

