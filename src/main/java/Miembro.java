import java.util.List;

enum TIPO_DOCUMENTO{
  DNI, PASAPORTE;
}

public class Miembro {
  String nombre;
  String apellido;
  TIPO_DOCUMENTO tipoDocumento;
  int numeroDocumento;
  List<Organizacion> Organizaciones;
  List<Trayecto> trayectos;
  //Los trayectos existen y se añaden o el miembro tiene la facultad de crear a voluntad?
  void registrarTrayecto(Trayecto unTrayecto){
  unTrayecto.add(trayectos);
  }
  void vincularseCon(Sector unSector){

  }

}
class MiembroBuilder{

}

