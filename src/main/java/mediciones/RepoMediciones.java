package mediciones;

import organizacion.Organizacion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepoMediciones {
  private final List<Medicion> mediciones = new ArrayList<>();
  private static RepoMediciones repoMediciones = null;

  private RepoMediciones() {
  }

  public static RepoMediciones getInstance() {
    if (repoMediciones == null) {
      repoMediciones = new RepoMediciones();
    }
    return repoMediciones;
  }

  public void cargarMedicion(Medicion medicion) {
    mediciones.add(medicion);
  }

  public int medicionesTotales() {
    return mediciones.size();
  }

  public List<Medicion> medicionesDe(Organizacion organizacion) {
    return this.mediciones
        .stream()
        .filter(medicion -> medicion.perteneceA(organizacion))
        .collect(Collectors.toList());
  }
}
