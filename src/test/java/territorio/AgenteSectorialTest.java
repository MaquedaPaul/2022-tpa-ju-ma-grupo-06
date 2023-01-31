package territorio;
import org.junit.jupiter.api.Test;
import organizacion.periodo.Periodo;
import organizacion.periodo.PeriodoMensual;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class AgenteSectorialTest {

    @Test
    public void sePuedeCrearUnAgenteSectorialYObtenerSuSector(){
        SectorTerritorial sectorTerritorial = new SectorTerritorial(new ArrayList<>(), TipoSectorTerritorial.PROVINCIA, "Chubut");
        SectorTerritorial otroSectorTerritorial = new SectorTerritorial(new ArrayList<>(), TipoSectorTerritorial.PROVINCIA, "Buenos Aires");
        AgenteTerritorial agente = new AgenteTerritorial(sectorTerritorial,"Pedro");
        assertEquals(agente.getNombre(), "Pedro");
        assertEquals(agente.getSectorTerritorial(), sectorTerritorial);
        agente.setNombre("Juan");
        assertEquals(agente.getNombre(), "Juan");
        agente.setSector(otroSectorTerritorial);
        assertEquals(agente.getSector(), otroSectorTerritorial);
    }

}
