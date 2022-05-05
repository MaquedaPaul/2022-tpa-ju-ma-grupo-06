import java.util.List;

public class Sector {
  String nombre;
  List<Miembro> miembros;

  public Sector(String nombre,List<Miembro> unosMiembros) {
    //un sector podría estar vacio? si todavía no se asignaron supongo que si
    this.nombre = nombre;
    this.miembros = unosMiembros;
  }

  void admitirMiembro(Miembro unMiembro) {
    miembros.add(unMiembro);
  }

  List<Miembro> getMiembros() {
    return miembros;
  }

  String getNombre() {
    return nombre;
  }


}
