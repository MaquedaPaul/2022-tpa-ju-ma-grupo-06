import static java.util.Objects.requireNonNull;

import java.util.List;

class MiembroBuilder {
  String nombre;
  String apellido;
  TIpoDocumento tipoDocumento;
  int numeroDocumento;
  List<Organizacion> organizaciones;
  List<Trayecto> trayectos;

  public MiembroBuilder() {

  }

  public Miembro construir() {
    this.validar();
    return new Miembro(nombre, apellido, tipoDocumento, numeroDocumento, organizaciones, trayectos);
  }

  public void especificarNombre(String unNombre) {
    this.nombre = requireNonNull(unNombre);
  }

  public void especificarApellido(String unApellido) {
    requireNonNull(unApellido);
    apellido = unApellido;
  }

  public void especificarTipoDocumento(TIpoDocumento unTipoDocumento) {
    requireNonNull(unTipoDocumento);
    tipoDocumento = unTipoDocumento;
  }

  public void especificarNumeroDocumento(int unNumeroDocumento) {
    numeroDocumento = unNumeroDocumento;
  }

  public void especificarOrganizaciones(List<Organizacion> unasOrganizaciones) {
    requireNonNull(unasOrganizaciones);
    organizaciones = unasOrganizaciones;
  }

  public void especificarTrayectos(List<Trayecto> unosTrayectos) {
    // podría ser que no se desplace porque es virtual
    trayectos = unosTrayectos;
  }

  public void validar() {
    requireNonNull(nombre);
    requireNonNull(apellido);
    requireNonNull(tipoDocumento);
    requireNonNull(organizaciones);
  }

}


