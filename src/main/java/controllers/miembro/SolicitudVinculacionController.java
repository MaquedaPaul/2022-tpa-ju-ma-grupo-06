package controllers.miembro;

import miembro.Miembro;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import organizacion.Organizacion;
import organizacion.Sector;
import organizacion.Solicitud;
import repositorios.RepoOrganizacion;
import repositorios.RepoSolicitud;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SolicitudVinculacionController implements WithGlobalEntityManager {
    public ModelAndView getVinculacion(Request request, Response response) {

        HashMap<String, Object> hashMap = new HashMap<>();
        camposDeVinculacionEnModel(request, hashMap);
        camposAttributeEnModel(request, hashMap);
        limpiarSessionErroresYExitos(request);

        return new ModelAndView(hashMap,"miembroVinculacion.hbs");
    }

    private void limpiarSessionErroresYExitos(Request request) {
        request.session().removeAttribute("errorOrganizacion");
        request.session().removeAttribute("errorSector");
        request.session().removeAttribute("exitoVinculacion");
        request.session().removeAttribute("yaHayVinculacionPendiente");
    }

    private void camposDeVinculacionEnModel(Request request, HashMap<String, Object> hashMap) {
        Miembro miembro = MiembroController.obtenerMiembro(request);
        List<Organizacion> organizaciones =  RepoOrganizacion.getInstance().getOrganizaciones();
        List<Organizacion> organizacionesQueNoPertenece = organizaciones.stream().filter(org -> !org.miembroPerteneceAlaOrganizacion(miembro)).collect(Collectors.toList());
        List<String> nombreSectores = new ArrayList<>(RepoOrganizacion.getInstance().nombreDeTodosLosSectores());

        hashMap.put("nombreSectores",nombreSectores);
        hashMap.put("organizaciones", organizacionesQueNoPertenece);
        if(organizacionesQueNoPertenece.isEmpty()){
            hashMap.put("sinOrganizaciones", true);
        }
    }

    private void camposAttributeEnModel(Request request, HashMap<String, Object> hashMap) {
        Object attributeErrorSector = request.session().attribute("errorSector");
        Object attributeErrorOrganizacion = request.session().attribute("errorOrganizacion");
        Object attributeExito = request.session().attribute("exitoVinculacion");
        Object attributeVinculacionExistente = request.session().attribute("yaHayVinculacionPendiente");

        boolean errorSector = attributeErrorSector != null && attributeErrorSector.equals(true);
        boolean errorOrganizacion = attributeErrorOrganizacion != null && attributeErrorOrganizacion.equals(true);
        boolean exitoVinculacion = attributeExito != null && attributeExito.equals(true);
        boolean yaHayVinculacionPendiente = attributeVinculacionExistente != null && attributeVinculacionExistente.equals(true);

        if(errorSector){
            hashMap.put("errorSector", true);
        }
        if(errorOrganizacion){
            hashMap.put("errorOrganizacion", true);
        }
        if(exitoVinculacion){
            hashMap.put("exitoVinculacion", true);
        }
        if(yaHayVinculacionPendiente){
            hashMap.put("yaHayVinculacionPendiente", true);
        }
    }


    public Response pedirVinculacion(Request request, Response response) {

        String organizacionSolicitada = request.queryParams("organizacionSolicitada");
        String sectorSolicitado = request.queryParams("sectorSolicitado");
        Organizacion organizacionObjetivo = RepoOrganizacion.getInstance().getOrganizacionPor(organizacionSolicitada);

        if (organizacionObjetivo == null) {
            request.session().attribute("errorOrganizacion",true);
            response.redirect("/home/vinculacion");
            return response;
        }
        Sector sectorObjetivo = organizacionObjetivo.obtenerSectorPor(sectorSolicitado);
        if (sectorObjetivo == null) {
            request.session().attribute("errorSector",true);
            response.redirect("/home/vinculacion");
            return response;
        }
        Miembro miembro =  MiembroController.obtenerMiembro(request);
        if(!RepoSolicitud.getInstance().miembroTieneSolicitudConOrg(miembro, organizacionObjetivo)){
            entityManager().getTransaction().begin();
            miembro.solicitarVinculacion(organizacionObjetivo, new Solicitud(miembro, sectorObjetivo));
            RepoOrganizacion.getInstance().agregarOrganizacion(organizacionObjetivo);
            entityManager().getTransaction().commit();
            request.session().attribute("exitoVinculacion",true);
            response.redirect("/home/vinculacion");
            return response;
        }
        request.session().attribute("yaHayVinculacionPendiente", true);
        response.redirect("/home/vinculacion");
        return response;
    }
}
