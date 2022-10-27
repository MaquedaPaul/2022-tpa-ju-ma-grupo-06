package server;

import cuenta.*;
import miembro.Miembro;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import organizacion.Organizacion;
import organizacion.TipoDocumento;
import organizacion.TipoOrganizacion;
import territorio.AgenteTerritorial;
import territorio.SectorTerritorial;
import territorio.TipoSectorTerritorial;

import java.util.ArrayList;

public class Bootstraps implements WithGlobalEntityManager {
  public static void init() {
    MiembroCuenta miembroCuenta = new MiembroCuenta("lucas", "123");
    Miembro miembro = new Miembro("nombre", "apellido", TipoDocumento.DNI,3,new ArrayList<>());
    miembro.setCuenta(miembroCuenta);

    OrganizacionCuenta organizacionCuenta = new OrganizacionCuenta("onu", "123");
    Organizacion organizacion = new Organizacion("razon", TipoOrganizacion.INSTITUCION, "argentina", "sa", new ArrayList<>());
    organizacion.setCuenta(organizacionCuenta);

    AgenteCuenta agenteCuenta = new AgenteCuenta("007", "123");
    SectorTerritorial sectorTerritorial = new SectorTerritorial(new ArrayList<>(), TipoSectorTerritorial.DEPARTAMENTO);
    AgenteTerritorial  agenteTerritorial = new AgenteTerritorial(sectorTerritorial);
    agenteTerritorial.setCuenta(agenteCuenta);


    new Bootstraps().persistir(organizacion);
    new Bootstraps().persistir(miembro);
    new Bootstraps().persistir(agenteTerritorial);
  }

  public void persistir(Object object) {
    entityManager().getTransaction().begin();
    entityManager().persist(object);
    entityManager().getTransaction().commit();
  }
}
