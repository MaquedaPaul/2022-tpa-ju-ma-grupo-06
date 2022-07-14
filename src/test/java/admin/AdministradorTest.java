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

  @Test
  void modificarFactorDeEmision() {
    //El singleton esta manteniendo los objetos incluso entre test por eso el get(1) mas abajo
    assertEquals(RepoFactoresEmision.getInstance().factoresTotales(), 1);
    Administrador unAdministrador = new Administrador("ejemplo","esteEsUnEjemplo");
    FactorEmision unFactor = new FactorEmision(300, Unidad.LTS);
    RepoFactoresEmision repo = RepoFactoresEmision.getInstance();
    repo.incorporarFactor(unFactor);
    unAdministrador.modificarFactorDeEmision(unFactor,450);
    assertEquals(RepoFactoresEmision.getInstance().getFactoresEmision().get(1).getValor(), 450);
  }
}
