package organizacion;

import admin.config.ValoresGlobales;
import transporte.Trayecto;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

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

  public double calcularPromedioHCPorMiembroPorMes() {
    return this.calcularHCTotalDeMiembrosPorMes() / this.getCantidadMiembros();
  }

  public double calcularHCTotalDeMiembrosPorMes() {
    return ValoresGlobales.getInstance().getDiasDeTrabajo() * this.getTrayectosDeMiembros()
        .mapToDouble(Trayecto::calcularHC)
        .sum();
  }

  public Stream<Trayecto> getTrayectosDeMiembros() {
    return this.getMiembros()
        .stream()
        .map(Miembro::getTrayectos)
        .flatMap(Collection::stream)
        .distinct();
  }

  public int getCantidadMiembros() {
    return getMiembros().size();
  }

}
