package admin;

import global.Unidad;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdministradorTest {
  @Test
  void agregarUnFactorDeEmision() {
    Administrador unAdministrador = new Administrador("ejemplo","esteEsUnEjemplo");
    unAdministrador.crearFactorEmision(300, Unidad.LTS);
    assertEquals(RepoFactoresEmision.getInstance().factoresTotales(), 1);
  }
}
