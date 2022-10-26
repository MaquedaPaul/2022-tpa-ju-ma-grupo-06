package territorio;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import organizacion.periodo.PeriodoMensual;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<HCPorSectorTerritorial> reporteHCPorSectorTerritorial(PeriodoMensual inicio, PeriodoMensual fin) {

        return this.getSectoresTerritoriales()
            .stream()
            .map(sector -> this.generarItemHCPorSectorTerritorial(sector, inicio, fin))
            .collect(Collectors.toList());
    }

    private HCPorSectorTerritorial generarItemHCPorSectorTerritorial(SectorTerritorial sector, PeriodoMensual inicio, PeriodoMensual fin) {
        return new HCPorSectorTerritorial(sector.calcularHCEntre(inicio, fin), sector);
    }

    public List<ComposicionHcSectorTerritorial> reporteComposicionHC(PeriodoMensual inicio, PeriodoMensual fin) {

        return this.getSectoresTerritoriales()
            .stream()
            .map(sector -> sector.generarItemComposicionHCEntre(inicio, fin))
            .collect(Collectors.toList());
    }

}
