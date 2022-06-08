package admin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AdministradorTest {

  @Test
  void agregarUnFactorDeEmision() {
    Administrador unAdministrador = new Administrador("ejemplo","esteEsUnEjemplo");
    RepoFactoresEmision unRepo = new RepoFactoresEmision();
    unAdministrador.setRepoDeFactores(unRepo);
    unAdministrador.crearFactorEmision(300,"lts");
    assertEquals(unRepo.getFactoresEmision().size(), 1);
  }
}
