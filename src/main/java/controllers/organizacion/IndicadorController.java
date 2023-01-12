package controllers.organizacion;

import organizacion.Organizacion;
import organizacion.Sector;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndicadorController {
    public ModelAndView getIndicadorHcSector(Request request, Response response) {
        String nombreSector = request.queryParams("sector");

        response.redirect("/home/calculadora-hc/indicador-hc-sector/"+nombreSector);
        return null;
    }

    public ModelAndView getIndicadorHcSectorBuscar(Request request, Response response) {
        Organizacion organizacion = OrganizacionController.obtenerOrganizacion(request);
        List<Sector> sectoresOrganizacion = organizacion.getSectores();
        Map<String, Object> model = new HashMap<>();
        model.put("sectores", sectoresOrganizacion);
        return new ModelAndView(model, "organizacionIndicadorHcSector.hbs");
    }

}
