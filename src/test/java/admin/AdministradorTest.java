package admin;

import global.Unidad;
import org.junit.jupiter.api.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import repositorios.RepoFactoresEmision;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdministradorTest implements WithGlobalEntityManager {


  @Test
  void agregarUnFactorDeEmision() {
    Administrador unAdministrador = new Administrador("ejemplo", "esteEsUnEjemplo");
    FactorEmision nuevoFactor = new FactorEmision(300, Unidad.LTS);
    entityManager().getTransaction().begin();
    unAdministrador.crearFactorEmision(nuevoFactor);
    entityManager().getTransaction().commit();
    entityManager().close();
    assertEquals(RepoFactoresEmision.getInstance().factoresTotales(), 1);
  }

  @Test
  void modificarFactorDeEmision() {
    Administrador unAdministrador = new Administrador("ejemplo", "esteEsUnEjemplo");
    FactorEmision unFactor = new FactorEmision(300, Unidad.LTS);
    RepoFactoresEmision repo = RepoFactoresEmision.getInstance();
    entityManager().getTransaction().begin();
    repo.incorporarFactor(unFactor);
    unAdministrador.modificarFactorDeEmision(unFactor, 450);
    entityManager().getTransaction().commit();
    entityManager().close();
    assertEquals(RepoFactoresEmision.getInstance().getFactoresEmision().get(0).getValor(), 450);
  }
}
