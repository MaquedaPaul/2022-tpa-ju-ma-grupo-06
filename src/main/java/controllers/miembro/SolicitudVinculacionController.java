package controllers.miembro;

import miembro.Miembro;
import organizacion.Organizacion;
import organizacion.Sector;
import organizacion.Solicitud;
import repositorios.RepoOrganizacion;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SolicitudVinculacionController {
    public ModelAndView getVinculacion(Request request, Response response) {

        List<Organizacion> organizaciones =  RepoOrganizacion.getInstance().getOrganizaciones();
        List<String> nombreSectores = new ArrayList<>(RepoOrganizacion.getInstance().nombreDeTodosLosSectores());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("nombreSectores",nombreSectores);
        hashMap.put("organizaciones", organizaciones);
        return new ModelAndView(hashMap,"miembroVinculacion.hbs");
    }
    public ModelAndView pedirVinculacion(Request request, Response response) {

        String organizacionSolicitada = request.queryParams("organizacionSolicitada");
        String sectorSolicitado = request.queryParams("sectorSolicitado");
        Organizacion organizacionObjetivo = RepoOrganizacion.getInstance().getOrganizacionPor(organizacionSolicitada);

        if (organizacionObjetivo == null) {
            response.redirect("/home/vinculacion");
            return null;
        }
        Sector sectorObjetivo = organizacionObjetivo.obtenerSectorPor(sectorSolicitado);
        if (sectorObjetivo == null) {
            response.redirect("/home/vinculacion");
            return null;
        }
        Miembro miembro =  MiembroController.obtenerMiembro(request);
        miembro.solicitarVinculacion(organizacionObjetivo, new Solicitud(miembro, sectorObjetivo));
        RepoOrganizacion.getInstance().agregarOrganizacion(organizacionObjetivo);
        response.redirect("/home");
        return null;
    }
}
