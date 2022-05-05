import java.util.List;
import static java.util.Objects.requireNonNull;

enum TIPO_DOCUMENTO{
  DNI, PASAPORTE //falta especificación de tipos de documentos
}

public class Miembro {
  String nombre;
  String apellido;
  TIPO_DOCUMENTO tipoDocumento;
  int numeroDocumento;
  List<Organizacion> organizaciones;
  List<Trayecto> trayectos;
  public Miembro(
      String unNombre,
      String unApellido,
      TIPO_DOCUMENTO unTipoDocumento,
      int unNumeroDocumento,
      List<Organizacion> unasOrganizaciones,
      List<Trayecto> unosTrayectos){
    nombre = unNombre;
    apellido = unApellido;
    tipoDocumento = unTipoDocumento;
    numeroDocumento= unNumeroDocumento;
    organizaciones= unasOrganizaciones;
    trayectos= unosTrayectos;
  }


  void registrarTrayecto(Trayecto unTrayecto){
    requireNonNull(unTrayecto);
    trayectos.add(unTrayecto);
  }
  void solicitarVinculacion(Organizacion unaOrganizacion, Sector unSector){
    unaOrganizacion.procesarPedidoVinculacion(unSector, this);
  }

}
class MiembroBuilder{
  String nombre;
  String apellido;
  TIPO_DOCUMENTO tipoDocumento;
  int numeroDocumento;
  List<Organizacion> organizaciones;
  List<Trayecto> trayectos;

  Miembro construir(){
    this.validar();
    Miembro unMiembro = new Miembro(nombre,apellido,tipoDocumento,numeroDocumento,organizaciones,trayectos);
    return unMiembro;
  }
  void especificarNombre(String unNombre){
    requireNonNull(unNombre);
    nombre = unNombre;
  }
  void especificarApellido(String unApellido){
    requireNonNull(unApellido);
    apellido = unApellido;
  }
  void especificarTipoDocumento(TIPO_DOCUMENTO unTipoDocumento){
    requireNonNull(unTipoDocumento);
    tipoDocumento = unTipoDocumento;
  }
  void especificarNumeroDocumento(int unNumeroDocumento){
    numeroDocumento = unNumeroDocumento;
  }
  void especificarOrganizaciones(List<Organizacion> unasOrganizaciones){
    requireNonNull(unasOrganizaciones);
    organizaciones = unasOrganizaciones;
  }
  void especificarTrayectos(List<Trayecto> unosTrayectos){
    //podría ser que no se desplace porque es virtual
    trayectos = unosTrayectos;
  }
  void validar(){
    requireNonNull(nombre);
    requireNonNull(apellido);
    requireNonNull(tipoDocumento);
    requireNonNull(organizaciones);
  }

}

