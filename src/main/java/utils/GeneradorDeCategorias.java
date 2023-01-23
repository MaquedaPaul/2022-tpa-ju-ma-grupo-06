package utils;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import repositorios.RepoTransporte;

import java.util.List;
import java.util.stream.Collectors;

public class GeneradorDeCategorias implements WithGlobalEntityManager {

  public List<CategoriaEInstanciasDeTransporte> generar() {
    entityManager().getTransaction().begin();
    List<String> tipos = RepoTransporte.Instance.getTiposTransportes();
    entityManager().getTransaction().commit();
    return this.transportesPorTipo(tipos);
  }

  private List<CategoriaEInstanciasDeTransporte> transportesPorTipo(List<String> tipos) {
    return tipos.stream().map(this::generarItem).collect(Collectors.toList());
  }

  private CategoriaEInstanciasDeTransporte generarItem(String tipo) {
    return new CategoriaEInstanciasDeTransporte(tipo,RepoTransporte.Instance.queryTransportesPor(tipo));
  }
}
