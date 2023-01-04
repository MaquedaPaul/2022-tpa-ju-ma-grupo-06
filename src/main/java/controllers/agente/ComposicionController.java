package controllers.agente;

import cuenta.AgenteCuenta;
import organizacion.Organizacion;
import organizacion.periodo.PeriodoMensual;
import reporte.ReporteOrganizaciones;
import reporte.itemsreportes.ComposicionHCOrganizacion;
import reporte.itemsreportes.ComposicionHcSectorTerritorial;
import repositorios.RepoOrganizacion;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import territorio.AgenteTerritorial;
import territorio.SectorTerritorial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComposicionController {
    public ModelAndView getComposicionHc(Request request, Response response) {

        AgenteCuenta cuentaUser = request.session().attribute("cuenta");

        return new ModelAndView(cuentaUser,"agenteComposicionHCConsulta.hbs");
    }

    public ModelAndView getComposicionHcGrafico(Request request, Response response) {
        
        //OBTENER PERIODOS
        PeriodoMensual inicio = AgenteController.getPeriodoInicio(request);
        PeriodoMensual fin = AgenteController.getPeriodoFin(request);

        //VALIDAR PERIODOS
        if(inicio.esDespuesDe(fin.getFecha())) {
            response.redirect("/home/composicion-hc");
            return null;
        }

        AgenteTerritorial agente = request.session().attribute("agente");
        //OBTENER SECTOR
        SectorTerritorial sector = agente.getSectorTerritorial();
        List<ComposicionHcSectorTerritorial> items = AgenteController.genReporte.reporteComposicionHC(inicio, fin, sector);

        Map<String,Object> model = new HashMap<>();
        model.put("items",items);
        model.put("sector",sector);
        model.put("inicio",inicio);
        model.put("fin",fin);

        return new ModelAndView(model,"agenteComposicionHCRespuesta.hbs");

    }
    public ModelAndView getComposicionHcOrg(Request request, Response response) {

        AgenteCuenta cuenta = request.session().attribute("cuenta");
        Long idOrg = Long.valueOf(request.params("id"));
        Organizacion org = RepoOrganizacion.getInstance().getOrganizacionById(idOrg);

        Map<String,Object> model = new HashMap<>();
        model.put("usuario",cuenta.getUsuario());
        model.put("nombreOrg",org.getRazonSocial());
        model.put("id",org.getId());

        return new ModelAndView(model,"agenteOrgComposicionHCConsulta.hbs");
    }

    public ModelAndView getComposicionHcOrgGrafico(Request request, Response response) {

        PeriodoMensual inicio = AgenteController.getPeriodoInicio(request);
        PeriodoMensual fin = AgenteController.getPeriodoFin(request);

        Long idOrg = Long.valueOf(request.params("id"));
        Organizacion org = RepoOrganizacion.getInstance().getOrganizacionById(idOrg);

        List<ComposicionHCOrganizacion> items = new ReporteOrganizaciones(RepoOrganizacion.getInstance())
                .composicionHCEntre(org,inicio, fin);

        Map<String,Object> model = new HashMap<>();
        model.put("nombreOrg",org.getRazonSocial());
        model.put("items",items);
        model.put("inicio",inicio);
        model.put("fin",fin);

        return new ModelAndView(model,"agenteOrgComposicionHCRespuesta.hbs");

    }

}
