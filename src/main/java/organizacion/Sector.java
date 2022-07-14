package organizacion;

import java.util.List;

public class Sector {
  String nombre;
  List<Miembro> miembros;

  public Sector(String nombre, List<Miembro> unosMiembros) {
    //un sector podría estar vacio? si todavía no se asignaron supongo que si
    this.nombre = nombre;
    this.miembros = unosMiembros;
  }

  public void admitirMiembro(Miembro unMiembro) {
    miembros.add(unMiembro);
  }

  public List<Miembro> getMiembros() {
    return miembros;
  }

  public String getNombre() {
    return nombre;
  }

  public double calcularHCMiembros() {
    return miembros.stream().mapToDouble(unMiembro -> unMiembro.calcularHCTotal()).sum();
  }

  public int getCantidadMiembros() {
    return getMiembros().size();
  }
}
