package organizacion;


import transporte.Trayecto;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class MiembroBuilder {
  String nombre;
  String apellido;
  TipoDocumento tipoDocumento;
  int numeroDocumento;
  List<Trayecto> trayectos;
  int cantidadDeDiasQueTrabaja = 20;

  public MiembroBuilder() {

  }

  public Miembro construir() {
    this.validar();
    return new Miembro(nombre, apellido, tipoDocumento, numeroDocumento, trayectos, cantidadDeDiasQueTrabaja);
  }

  public void especificarNombre(String unNombre) {
    this.nombre = requireNonNull(unNombre);
  }

  public void especificarApellido(String unApellido) {
    requireNonNull(unApellido);
    apellido = unApellido;
  }

  public void especificarTipoDocumento(TipoDocumento unTipoDocumento) {
    requireNonNull(unTipoDocumento);
    tipoDocumento = unTipoDocumento;
  }

  public void especificarNumeroDocumento(int unNumeroDocumento) {
    numeroDocumento = unNumeroDocumento;
  }

  public void especificarTrayectos(List<Trayecto> unosTrayectos) {
    trayectos = unosTrayectos;
  }

  public void especificarCantidadDiasQueTrabaja(int cantidadDeDiasQueTrabaja) {
    this.cantidadDeDiasQueTrabaja = cantidadDeDiasQueTrabaja;
  }

  public void validar() {
    requireNonNull(nombre);
    requireNonNull(apellido);
    requireNonNull(tipoDocumento);
  }
}


