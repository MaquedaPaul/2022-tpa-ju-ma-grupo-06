package tipoconsumo;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;

public class RepoTipoDeConsumo implements WithGlobalEntityManager {

  private static RepoTipoDeConsumo repoTipoConsumo = null;

  private RepoTipoDeConsumo() {
  }

  public static RepoTipoDeConsumo getInstance() {
    if (repoTipoConsumo == null) {
      repoTipoConsumo = new RepoTipoDeConsumo();
    }
    return repoTipoConsumo;
  }

  public void agregarNuevoTipoDeConsumo(TipoConsumo nuevoTipoConsumo) {
    entityManager().persist(nuevoTipoConsumo);
    entityManager().refresh(nuevoTipoConsumo);
  }

  @SuppressWarnings("unchecked")
  public List<TipoConsumo> getTipoConsumo() {
    return entityManager().createQuery("from TipoConsumo").getResultList();
  }

  public boolean existeElTipoDeConsumo(TipoConsumo tipoConsumo) {
    List<TipoConsumo> tiposDeConsumoExistentes = this.getTipoConsumo();
    return tiposDeConsumoExistentes.contains(tipoConsumo);
  }
  //TODO
  //El metodo de arriba recibe un Tipo de Consumo o un String?
  // ,en el segundo caso habria que hacer lo de abajo

 /* public TipoConsumo getTipoDeConsumo(String nombre) {
    List<TipoConsumo> tipos = this.tiposDeConsumos
        .stream()
        .filter(tipo -> Objects.equals(tipo.getNombre(), nombre))
        .collect(Collectors.toList());
    if (tipos.isEmpty()) {
      return null;
    }
    return tipos.get(0);
    */
  }