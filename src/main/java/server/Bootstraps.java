package server;

import cuenta.*;
import miembro.Miembro;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import organizacion.Organizacion;
import organizacion.Sector;
import organizacion.TipoDocumento;
import organizacion.TipoOrganizacion;
import organizacion.repositorio.RepoOrganizacion;
import territorio.AgenteTerritorial;
import territorio.SectorTerritorial;
import territorio.TipoSectorTerritorial;

import java.util.ArrayList;

public class Bootstraps implements WithGlobalEntityManager {
  public static void init() {
    MiembroCuenta miembroCuenta = new MiembroCuenta("lucas", "123");
    Miembro miembro = new Miembro("lucas", "blanco", TipoDocumento.DNI,3,new ArrayList<>());
    miembro.setCuenta(miembroCuenta);

    OrganizacionCuenta cuentaOnu = new OrganizacionCuenta("onu", "123");
    OrganizacionCuenta cuentaSerenisima = new OrganizacionCuenta("serenisima", "123");
    Organizacion organizacion2 = new Organizacion("onu", TipoOrganizacion.INSTITUCION, "argentina", "sa", new ArrayList<>());
    Organizacion organizacion = new Organizacion("serenisima", TipoOrganizacion.INSTITUCION, "argentina", "sa", new ArrayList<>());
    organizacion2.setCuenta(cuentaOnu);
    organizacion.setCuenta(cuentaSerenisima);
    organizacion.incorporarSector(new Sector("Compras", new ArrayList<>()));
    organizacion.incorporarSector(new Sector("Ventas", new ArrayList<>()));

    organizacion2.incorporarSector(new Sector("Recursos Humanos", new ArrayList<>()));
    organizacion2.incorporarSector(new Sector("Administracion", new ArrayList<>()));
    organizacion2.incorporarSector(new Sector("Ventas", new ArrayList<>()));
    RepoOrganizacion.getInstance().agregarOrganizacion(organizacion);
    RepoOrganizacion.getInstance().agregarOrganizacion(organizacion2);

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
