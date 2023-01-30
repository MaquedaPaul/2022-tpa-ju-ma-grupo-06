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
    public static Map<String, Object> getMiembrosImpactoOrg(Request request, Map<String, Object> model ) {
        Organizacion organizacion = OrganizacionController.obtenerOrganizacion(request);
        model.put("miembros", organizacion.getMiembros());
        model.put("conSeleccion", false);
        if(organizacion.getMiembros().isEmpty()){
            model.put("estaActivadoBoton", false);
            return model;
        }
        model.put("estaActivadoBoton", true);
        return model;
        //return new ModelAndView(model, "organizacionImpactoMiembro.hbs");
    }

    public ModelAndView getImpactoMiembro(Request request, Response response) {

        String nombreApellido = request.queryParams("miembro");
        response.redirect("/home/calculadora-hc/impacto-de-miembro/"+nombreApellido);
        return null;
    }

    public ModelAndView getImpactoMiembroConNombreYApellido(Request request, Response response) {
        Organizacion organizacion = OrganizacionController.obtenerOrganizacion(request);
        Map<String, Object> model = new HashMap<>();
        OrganizacionController.usuarioEnModel(model, request);
        String nombreApellido = request.params("nombreApellido");
        Miembro miembro = RepoMiembros.getInstance().getMiembrosPorNombreYApellido(nombreApellido);

        double impacto = organizacion.impactoDeMiembro(miembro,new PeriodoMensual(LocalDate.now()));
        double impacto2Decimales = (double) Math.round(impacto * 100) / 100;

        double valor = request.session().attribute("valorHc");
        modelPutImpactoNombreApellido(model,organizacion,miembro,impacto2Decimales,valor);
        return new ModelAndView(model, "organizacionHcTotalNew.hbs");
    }
    private Map<String, Object> modelPutImpactoNombreApellido(Map<String, Object> model, Organizacion organizacion, Miembro miembro, double impacto2Decimales, double valor){
        model.put("estaActivadoBoton", true);
        model.put("conSeleccion", true);
        model.put("miembros", organizacion.getMiembros());
        model.put("nombre",miembro.getNombre());
        model.put("apellido",miembro.getApellido());
        model.put("impacto",impacto2Decimales);
        model.put("valorhc",valor);
        return model;
    }


}
