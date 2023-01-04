package controllers.organizacion;

import organizacion.Organizacion;
import organizacion.Solicitud;
import repositorios.RepoOrganizacion;
import repositorios.RepoSolicitud;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class VinculacionController {
    public ModelAndView getVinculaciones(Request request, Response response) {
        Organizacion organizacion = OrganizacionController.obtenerOrganizacion(request);
        Map<String, Object> model = new HashMap<>();
        model.put("solicitudes",organizacion.getSolicitudesSinProcesar());
        return new ModelAndView(model, "organizacionGestionarVinculaciones.hbs");
    }

    private Solicitud procesarVinculacion(boolean aceptar, String usuario, Request request, Response response){
        Organizacion organizacion = OrganizacionController.obtenerOrganizacion(request);
        Long idVinculacion = Long.valueOf((request.params("id")));
        System.out.println(idVinculacion);
        //Solicitud solicitud = organizacion.getSolicitudPorId(idVinculacion);
        Solicitud solicitud = RepoSolicitud.getInstance().getSolicitudById(idVinculacion);
        organizacion.procesarVinculacion(solicitud,aceptar);
        RepoOrganizacion.getInstance().agregarOrganizacion(organizacion);
        return solicitud;
    }


    public ModelAndView aceptarVinculacion(Request request, Response response) {
        String usuario = OrganizacionController.obtenerUsuario(request);
        Solicitud solicitud = procesarVinculacion(true,usuario,request,response);
        Map<String, Object> model = new HashMap<>();
        model.put("miembroAceptado", true);
        model.put("solicitud",solicitud);
        return new ModelAndView(model,"organizacionGestionarVinculaciones.hbs");
    }


    public ModelAndView rechazarVinculacion(Request request, Response response) {
        String usuario = OrganizacionController.obtenerUsuario(request);
        Solicitud solicitud = procesarVinculacion(false,usuario,request,response);
        Map<String, Object> model = new HashMap<>();
        model.put("miembroRechazado", true);
        model.put("solicitud",solicitud);
        return new ModelAndView(model,"organizacionGestionarVinculaciones.hbs");
    }
}
