package territorio;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

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

    @SuppressWarnings("unchecked")
    public List<SectorTerritorial> getSectoresTerritoriales() {
        return entityManager().createQuery("FROM SectorTerritorial").getResultList();
    }

    public void registrarSectorTerritorial(SectorTerritorial sector) {
        entityManager().persist(sector);
        entityManager().refresh(sector);
    }



}
