package tipo.consumo;

import java.util.ArrayList;
import java.util.List;

public class RepoTipoDeConsumo {
  public static List<TipoConsumo> tiposDeConsumos = new ArrayList<>();

  private RepoTipoDeConsumo() {
  }

  void agregarNuevoTipoDeConsumo(TipoConsumo nuevoTipoConsumo) {
    tiposDeConsumos.add(nuevoTipoConsumo);
  }
}
