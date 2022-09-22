package territorio;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import organizacion.RepoOrganizacion;
import registrohc.RegistroHCOrganizacion;
import registrohc.RepoMedicionesHCOrganizaciones;

import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    //CADA LISTA DE ORGS ES UN SECTOR
    //CADA STREAM DE MEDICIONES ES UNA ORG
    //CADA STREAM DE LONGS ERAN UNA ORG
    //CADA LONG DEL SUM DEL STREAM ES UNA ORG
    //-> CADA LONG ES UN SECTOR
    public List<HCPorSectorTerritorial> hcPorSectorTerritorial(YearMonth fechaInicio, YearMonth fechaFin) {

        //QUILMES 2000 07/2000
        //QUILMES 2020 08/2000
        List<SectorTerritorial> sectoresTerritoriales = this.getSectoresTerritoriales();
        List<Long> resultadosHC = sectoresTerritoriales.stream()
            .map(SectorTerritorial::getOrganizaciones)
            .map(lista -> lista.stream()
                .map(org -> RepoMedicionesHCOrganizaciones
                    .getInstance().getRegistros(org, fechaInicio, fechaFin)
                    .stream()
                    .mapToLong(RegistroHCOrganizacion::hcTotal).sum()))
            .map(longStream -> longStream.collect(Collectors.toList()))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        return generarReporteHCSector(sectoresTerritoriales, resultadosHC);
    }

    public List<HCPorSectorTerritorial> generarReporteHCSector(List<SectorTerritorial> sectores, List<Long> resultados) {

        return sectores.stream()
            .map(sector -> new HCPorSectorTerritorial(resultados.get(sectores.indexOf(sector)), sector, null))
            .collect(Collectors.toList());
    }

    //TODO ADD GENERAR REPORTE
    //Pensar en injectar algun objeto para flexibilizar este metodo
    public List<HCPorSectorTerritorial> evolucionHCTotal(SectorTerritorial sector, YearMonth inicio, YearMonth fin) {

        Stream<RegistroHCOrganizacion> registros = sector
            .getOrganizaciones().stream()
            .map(org -> RepoOrganizacion.getInstance().evolucionHCTotal(org, inicio, fin).stream())
            .flatMap(Stream::sorted);

        Stream<YearMonth> periodo = registros
            .map(registro -> YearMonth.of(registro.getAnioImputacion(), registro.getMesImputacion()))
            .distinct();

        return periodo.map(fecha -> new HCPorSectorTerritorial(
                registros.filter(registro -> YearMonth.of(registro.getAnioImputacion(), registro.getMesImputacion()).equals(fecha))
                    .mapToLong(RegistroHCOrganizacion::hcTotal)
                    .sum(), sector, fecha))
            .collect(Collectors.toList());

    }
/*
    public List<ComposicionHcSectorTerritorial> composicionHcSectorTerritorial(SectorTerritorial sectorTerritorial,
                                                                               YearMonth inicio,
                                                                               YearMonth fin) {

        List<Organizacion> organizaciones = RepoOrganizacion.getInstance().getOrganizaciones();

        Stream<TipoConsumo> tipos = organizaciones.stream()
            .map(Organizacion::getTiposDeConsumoUsados)
            .flatMap(Stream::distinct);

        List<Medicion> mediciones = RepoMediciones.getInstance().getMedicionesEntre(inicio, fin);

    }

 */
}
