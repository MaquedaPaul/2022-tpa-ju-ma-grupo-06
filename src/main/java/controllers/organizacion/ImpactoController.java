package controllers.organizacion;

import miembro.Miembro;
import organizacion.Organizacion;
import organizacion.periodo.PeriodoMensual;
import repositorios.RepoMiembros;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ImpactoController {
    public ModelAndView getImpactoMiembroBuscar(Request request, Response response) {
        Organizacion organizacion = OrganizacionController.obtenerOrganizacion(request);

        Map<String, Object> model = new HashMap<>();
        model.put("miembros", organizacion.getMiembros());
        model.put("conSeleccion", false);
        if(organizacion.getMiembros().isEmpty()){
            model.put("estaActivadoBoton", false);
            return new ModelAndView(model, "organizacionImpactoMiembro.hbs");
        }
        model.put("estaActivadoBoton", true);
        return new ModelAndView(model, "organizacionImpactoMiembro.hbs");
    }

    public ModelAndView getImpactoMiembro(Request request, Response response) {


        String nombreApellido = request.queryParams("miembro");
        response.redirect("/home/calculadora-hc/impacto-de-miembro/"+nombreApellido);

        return null;
    }

    public ModelAndView getImpactoMiembroConNombreYApellido(Request request, Response response) {
        Organizacion organizacion = OrganizacionController.obtenerOrganizacion(request);
        Map<String, Object> model = new HashMap<>();
        model.put("estaActivadoBoton", true);
        model.put("conSeleccion", true);
        model.put("miembros", organizacion.getMiembros());

        String nombreApellido = request.params("nombreApellido");
        Miembro miembro = RepoMiembros.getInstance().getMiembrosPorNombreYApellido(nombreApellido);
        model.put("nombre",miembro.getNombre());
        model.put("apellido",miembro.getApellido());
        double impacto = organizacion.impactoDeMiembro(miembro,new PeriodoMensual(LocalDate.now()));
        model.put("impacto",impacto);

        return new ModelAndView(model, "organizacionImpactoMiembro.hbs");
    }

}
