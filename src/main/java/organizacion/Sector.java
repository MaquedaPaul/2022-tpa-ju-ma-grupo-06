package organizacion;

import admin.config.GestorDeFechas;
import miembro.Miembro;
import transporte.Trayecto;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Entity
public class Sector {

  //TODO puede ser que un miembro pueda estar en mas de un sector de distintas organizaciones
  @Id
  @GeneratedValue
  Long id;

  String nombre;

  @OneToMany(cascade = CascadeType.PERSIST)
  List<Miembro> miembros;

  public Sector(String nombre, List<Miembro> unosMiembros) {
    //un sector podría estar vacio? si todavía no se asignaron supongo que si
    this.nombre = nombre;
    this.miembros = unosMiembros;
  }

  public Sector() {
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
    double resultado =this.calcularHCTotalDeMiembrosPorMes() / this.getCantidadMiembros();
    if(Double.isNaN(resultado)){
      return 0;
    }
    return resultado;
  }

  public double calcularHCTotalDeMiembrosPorMes() {
    return GestorDeFechas.getInstance().getDiasDeTrabajo() * this.getTrayectosDeMiembros()
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
