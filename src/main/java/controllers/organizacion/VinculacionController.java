package controllers.organizacion;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import organizacion.Organizacion;
import organizacion.Solicitud;
import repositorios.RepoMediciones;
import repositorios.RepoMiembros;
import repositorios.RepoOrganizacion;
import repositorios.RepoSolicitud;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class VinculacionController implements WithGlobalEntityManager {
    public ModelAndView getVinculaciones(Request request, Response response) {
        Organizacion organizacion = OrganizacionController.obtenerOrganizacion(request);
        Map<String, Object> model = new HashMap<>();
        model.put("solicitudes",organizacion.getSolicitudesSinProcesar());
        OrganizacionController.usuarioEnModel(model, request);
        attributesEnModel(request, model);
        limpiarSessionAttributes(request);
        return new ModelAndView(model, "organizacionGestionarVinculaciones.hbs");
    }

    private void attributesEnModel(Request request,Map<String, Object> model ) {
        Object miembroAceptado = request.session().attribute("miembroAceptado");
        Object miembroRechazado = request.session().attribute("miembroRechazado");
        Object solicitud = request.session().attribute("solicitud");
        boolean miembroAceptadoNotNull = miembroAceptado != null;
        boolean miembroRechazadoNotNull = miembroRechazado != null;
        boolean solicitudNotNull = solicitud != null;
        if(miembroAceptadoNotNull){
        model.put("miembroAceptado", miembroAceptado);
        }
        if(miembroRechazadoNotNull){
            model.put("miembroRechazado", miembroRechazado);
        }
        if(solicitudNotNull){
            model.put("solicitud", solicitud);
        }


    }
    private void limpiarSessionAttributes(Request request){
        request.session().removeAttribute("miembroAceptado");
        request.session().removeAttribute("miembroRechazado");
        request.session().removeAttribute("solicitud");
    }


    private Solicitud procesarVinculacion(boolean aceptar, String usuario, Request request, Response response){
        Organizacion organizacion = OrganizacionController.obtenerOrganizacion(request);
        Long idVinculacion = Long.valueOf((request.params("id")));
        Solicitud solicitud = RepoSolicitud.getInstance().getSolicitudById(idVinculacion);
        entityManager().getTransaction().begin();
        organizacion.procesarVinculacion(solicitud,aceptar);
        RepoOrganizacion.getInstance().agregarOrganizacion(organizacion);
        entityManager().getTransaction().commit();
        return solicitud;
    }


    public Response aceptarVinculacion(Request request, Response response) {
        String usuario = OrganizacionController.obtenerUsuario(request);
        Solicitud solicitud = procesarVinculacion(true,usuario,request,response);
        request.session().attribute("miembroAceptado", true);
        request.session().attribute("solicitud", solicitud);
        response.redirect("/home/vinculaciones");
        return response;
    }


    public Response rechazarVinculacion(Request request, Response response) {
        String usuario = OrganizacionController.obtenerUsuario(request);
        Solicitud solicitud = procesarVinculacion(false,usuario,request,response);
        request.session().attribute("miembroRechazado", true);
        request.session().attribute("solicitud", solicitud);
        response.redirect("/home/vinculaciones");
        return response;
    }
}
