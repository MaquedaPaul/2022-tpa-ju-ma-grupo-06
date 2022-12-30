package controllers.agente;

import cuenta.AgenteCuenta;
import organizacion.Organizacion;
import organizacion.periodo.PeriodoMensual;
import reporte.ReporteOrganizaciones;
import reporte.itemsreportes.EvolucionHCOrganizacion;
import reporte.itemsreportes.EvolucionHCSectorTerritorial;
import repositorios.RepoOrganizacion;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import territorio.AgenteTerritorial;
import territorio.SectorTerritorial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvolucionController {
    public ModelAndView getEvolucionHc(Request request, Response response) {
        AgenteCuenta agenteCuenta = request.session().attribute("cuenta");

        return new ModelAndView(agenteCuenta,"agenteEvolucionHcConsulta.hbs");
    }

    public ModelAndView getEvolucionHcGrafico(Request request, Response response) {
        AgenteCuenta agenteCuenta = request.session().attribute("cuenta");

        PeriodoMensual inicio = AgenteController.getPeriodoInicio(request);
        PeriodoMensual fin = AgenteController.getPeriodoFin(request);

        AgenteTerritorial agente = request.session().attribute("agente");
        SectorTerritorial sector = agente.getSectorTerritorial();
        List<EvolucionHCSectorTerritorial> evols = AgenteController.genReporte.reporteEvolucionHC(sector,inicio,fin);

        Map<String,Object> model = new HashMap<>();
        model.put("items",evols);
        model.put("sector",sector);
        model.put("inicio",inicio);
        model.put("fin",fin);
        model.put("usuario",agenteCuenta.getUsuario());
        return new ModelAndView(model,"agenteEvolucionHcRespuesta.hbs");

    }
    public ModelAndView getEvolucionHcOrg(Request request, Response response) {

        AgenteCuenta cuenta = request.session().attribute("cuenta");
        Long idOrg = Long.valueOf(request.params("id"));
        Organizacion org = RepoOrganizacion.getInstance().getOrganizacionById(idOrg);
        Map<String,Object> model = new HashMap<>();
        model.put("usuario",cuenta.getUsuario());
        model.put("nombreOrg",org.getRazonSocial());
        model.put("id",request.params("id"));

        return new ModelAndView(model,"agenteOrgEvolucionHCConsulta.hbs");
    }

    public ModelAndView getEvolucionHcOrgGrafico(Request request, Response response) {

        //OBTENER PERIODOS
        PeriodoMensual inicio = AgenteController.getPeriodoInicio(request);
        PeriodoMensual fin = AgenteController.getPeriodoFin(request);

        AgenteCuenta cuenta = request.session().attribute("cuenta");
        Long idOrg = Long.valueOf(request.params("id"));
        Organizacion org = RepoOrganizacion.getInstance().getOrganizacionById(idOrg);

        //VALIDAR PERIODOS
        if(inicio.esDespuesDe(fin.getFecha())) {
            response.redirect("/home/organizaciones");
            return null;
        }

        List<EvolucionHCOrganizacion> items = new ReporteOrganizaciones(RepoOrganizacion.getInstance()).evolucionHCEntre(org,inicio,fin);

        Map<String,Object> model = new HashMap<>();
        model.put("usuario",cuenta.getUsuario());
        model.put("nombreOrg",org.getRazonSocial());
        model.put("id",org.getId());
        model.put("items",items);

        return new ModelAndView(model,"agenteOrgEvolucionHCRespuesta.hbs");

    }
}
