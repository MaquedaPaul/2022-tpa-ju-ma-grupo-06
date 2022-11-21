package repositorios;

import lectorcsv.RepoTipoConsumo;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import utils.tipoconsumo.TipoConsumo;

import java.util.List;
import java.util.stream.Collectors;

public class RepoTipoDeConsumo implements WithGlobalEntityManager, RepoTipoConsumo {

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
    entityManager().getTransaction().begin();
    entityManager().persist(nuevoTipoConsumo);
    entityManager().getTransaction().commit();
  }

  @SuppressWarnings("unchecked")
  public List<TipoConsumo> getTiposConsumo() {
    return entityManager().createQuery("from TipoConsumo").getResultList();
  }

  public boolean existeElTipoDeConsumo(String tipoConsumo) {

    List<TipoConsumo> tiposDeConsumoExistentes = this.getTiposConsumo();
    return tiposDeConsumoExistentes.stream().filter(tipo -> tipo.getNombre().toLowerCase().equals(tipoConsumo.toLowerCase())).count() == 1;
  }

  public TipoConsumo getTipoConsumo(String tipoConsumo) {

    return this.getTiposConsumo().stream()
        .filter(tipo -> tipo.getNombre().equalsIgnoreCase(tipoConsumo))
        .collect(Collectors.toList())
        .get(0);
  }

  public TipoConsumo getTipoConsumoById(long id) {
    return entityManager().find(TipoConsumo.class,id);
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