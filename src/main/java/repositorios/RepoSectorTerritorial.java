package repositorios;

import cuenta.AgenteCuenta;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import territorio.AgenteTerritorial;
import territorio.SectorTerritorial;

import java.util.List;

public class RepoSectorTerritorial implements WithGlobalEntityManager {

    private static RepoSectorTerritorial instance;

    private RepoSectorTerritorial() {
    }

    public static RepoSectorTerritorial getInstance() {
        if (instance == null) {
            instance = new RepoSectorTerritorial();
        }
        return instance;
    }

    public void registrarSectorTerritorial(SectorTerritorial sector) {
        entityManager().getTransaction().begin();
        entityManager().persist(sector);
        entityManager().getTransaction().commit();
    }

    public void registrarAgente(AgenteTerritorial agente) {
        entityManager().getTransaction().begin();
        entityManager().persist(agente);
        entityManager().getTransaction().commit();
    }

    public SectorTerritorial getSectorById(long id) {
        return entityManager().find(SectorTerritorial.class,id);
    }
}
