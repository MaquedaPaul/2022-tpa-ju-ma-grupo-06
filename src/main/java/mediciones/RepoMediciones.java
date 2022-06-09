package mediciones;

import java.util.ArrayList;
import java.util.List;

public class RepoMediciones {
  private final List<Medicion> mediciones = new ArrayList<>();
  private static RepoMediciones repoMediciones = null;

  private RepoMediciones() {
  }
  public static RepoMediciones getInstance() {
    if(repoMediciones == null) {
      repoMediciones = new RepoMediciones();
    }
    return repoMediciones;
  }

  public void cargarMedicion(Medicion medicion) {
    mediciones.add(medicion);
  }
}
