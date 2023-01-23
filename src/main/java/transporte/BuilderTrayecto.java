package transporte;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class BuilderTrayecto {
  private final List<Tramo> tramos = new ArrayList<>();
  private Tramo ultimoTramo;

  public void agregarTramo(Tramo tramo) {
    if (ultimoTramo != null) {
      tramos.add(ultimoTramo);
    }
    ultimoTramo = tramo;
  }

  public void eliminarUltimoTramo() {
    this.ultimoTramo = null;
  }

  public Trayecto build() {
    if (!this.sePuedeConstruir()) {
      throw new RuntimeException("No se puede construir el trayecto, debe tener al menos 1 tramo");
    }
    return new Trayecto(this.getSetTramos());
  }

  private Set<Tramo> getSetTramos() {
    Set<Tramo> setTramos = new HashSet<>(this.getTramos());
    setTramos.add(this.ultimoTramo);
    return setTramos;
  }

  public boolean sePuedeConstruir() {
    return this.getSetTramos().size() > 0;
  }

}
