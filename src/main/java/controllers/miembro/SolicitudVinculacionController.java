package controllers.miembro;

import miembro.Miembro;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import organizacion.Organizacion;
import organizacion.Sector;
import organizacion.Solicitud;
import repositorios.RepoMediciones;
import repositorios.RepoOrganizacion;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SolicitudVinculacionController implements WithGlobalEntityManager {
    public ModelAndView getVinculacion(Request request, Response response) {
        Miembro miembro = MiembroController.obtenerMiembro(request);
        List<Organizacion> organizaciones =  RepoOrganizacion.getInstance().getOrganizaciones();
        List<Organizacion> organizacionesQueNoPertenece = organizaciones.stream().filter(org -> !org.miembroPerteneceAlaOrganizacion(miembro)).collect(Collectors.toList());
        List<String> nombreSectores = new ArrayList<>(RepoOrganizacion.getInstance().nombreDeTodosLosSectores());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("nombreSectores",nombreSectores);
        hashMap.put("organizaciones", organizacionesQueNoPertenece);
        Object attributeErrorSector = request.session().attribute("errorSector");
        Object attributeErrorOrganizacion = request.session().attribute("errorOrganizacion");
        boolean errorSector = attributeErrorSector != null && attributeErrorSector.equals(true);
        boolean errorOrganizacion = attributeErrorOrganizacion != null && attributeErrorOrganizacion.equals(true);


        if(errorSector){
            hashMap.put("errorSector", true);
        }
        if(errorOrganizacion){
            hashMap.put("errorOrganizacion", true);
        }
        request.session().removeAttribute("errorOrganizacion");
        request.session().removeAttribute("errorSector");
        return new ModelAndView(hashMap,"miembroVinculacion.hbs");
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
        entityManager().getTransaction().begin();
        miembro.solicitarVinculacion(organizacionObjetivo, new Solicitud(miembro, sectorObjetivo));
        RepoOrganizacion.getInstance().agregarOrganizacion(organizacionObjetivo);
        entityManager().getTransaction().commit();

        response.redirect("/home");
        return response;
    }
}
