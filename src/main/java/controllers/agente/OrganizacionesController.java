package controllers.agente;

import cuenta.AgenteCuenta;
import global.Unidad;
import organizacion.Organizacion;
import organizacion.TipoOrganizacion;
import organizacion.periodo.PeriodoMensual;
import reporte.ReporteOrganizaciones;
import reporte.itemsreportes.HCPorTipoOrganizacion;
import repositorios.RepoOrganizacion;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import territorio.AgenteTerritorial;
import territorio.SectorTerritorial;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrganizacionesController {
    public ModelAndView getOrganizaciones(Request request, Response response) {

        AgenteCuenta cuenta = request.session().attribute("cuenta");
        AgenteTerritorial agente = request.session().attribute("agente");
        SectorTerritorial sector = agente.getSectorTerritorial();
        List<Organizacion> organizaciones = sector.getOrganizaciones();
        Map<String,Object> model = new HashMap<>();
        model.put("usuario",cuenta.getUsuario());
        model.put("organizaciones",organizaciones);

        return new ModelAndView(model,"agenteOrganizacion.hbs");
    }

    public ModelAndView getHcTipoOrg(Request request, Response response) {

        AgenteTerritorial agente = request.session().attribute("agente");
        AgenteCuenta cuenta = request.session().attribute("cuenta");
        PeriodoMensual periodo = new PeriodoMensual(LocalDate.now());
        PeriodoMensual periodo2 = new PeriodoMensual(LocalDate.now().plusDays(1L));
        HCPorTipoOrganizacion hcPorTipoOrganizacion = new ReporteOrganizaciones(RepoOrganizacion.getInstance())
                .hcPorTipoOrganizacion(periodo,periodo2)
                .stream()
                .filter(rep -> rep.getUnTipo() == TipoOrganizacion.valueOf(request.params("tipo"))).collect(Collectors.toList()).get(0);

        Map<String,Object> model = new HashMap<>();
        model.put("valorMensual",hcPorTipoOrganizacion.getHc());
        model.put("valorAnual",1000D);
        model.put("unidad", Unidad.LTS);
        model.put("sector",agente.getSectorTerritorial().getNombre());
        model.put("usuario",cuenta.getUsuario());
        model.put("periodoActual",periodo);
        model.put("tipo",hcPorTipoOrganizacion.getUnTipo());

        return new ModelAndView(model,"agenteHcTotalTipoOrg.hbs");
    }
}
