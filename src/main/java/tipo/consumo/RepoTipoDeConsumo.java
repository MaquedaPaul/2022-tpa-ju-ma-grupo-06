package tipo.consumo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RepoTipoDeConsumo {
  private final List<TipoConsumo> tiposDeConsumos = new ArrayList<>();
  public static RepoTipoDeConsumo repoTipoConsumo = null;

  private RepoTipoDeConsumo() {
  }

  public static RepoTipoDeConsumo getInstance() {
    if (repoTipoConsumo == null) {
      repoTipoConsumo = new RepoTipoDeConsumo();
    }
    return repoTipoConsumo;
  }

  public void agregarNuevoTipoDeConsumo(TipoConsumo nuevoTipoConsumo) {
    tiposDeConsumos.add(nuevoTipoConsumo);
  }

  public boolean existeElTipoDeConsumo(String tipoConsumo) {
    return this.getTipoDeConsumo(tipoConsumo) != null;
  }

  public TipoConsumo getTipoDeConsumo(String nombre) {
    return this.tiposDeConsumos
        .stream()
        .filter(tipo -> Objects.equals(tipo.getNombre(), nombre))
        .collect(Collectors.toList())
        .get(0);
  }
}
