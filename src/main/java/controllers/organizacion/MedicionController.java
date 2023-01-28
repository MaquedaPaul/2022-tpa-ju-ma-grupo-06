package controllers.organizacion;

import exceptions.*;
import lectorcsv.LectorMediciones;
import lectorcsv.TipoPerioricidad;
import mediciones.Medicion;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import organizacion.Organizacion;
import repositorios.RepoMediciones;
import repositorios.RepoTipoDeConsumo;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import tipoconsumo.TipoConsumo;

import javax.persistence.EntityManager;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.swing.text.html.parser.Entity;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map;

public class MedicionController implements WithGlobalEntityManager {

    public ModelAndView getMediciones(Request request, Response response) {
        Organizacion organizacion = OrganizacionController.obtenerOrganizacion(request);
        HashMap<String, Object> model = new HashMap<>();
        OrganizacionController.usuarioEnModel(model, request);
        model.put("organizacion", organizacion);
        return new ModelAndView(model, "organizacionRegistrarMediciones.hbs");
    }

    public ModelAndView getMedicionesArchivo(Request request, Response response) {
        Organizacion organizacion = OrganizacionController.obtenerOrganizacion(request);
        HashMap<String, Object> model = new HashMap<>();
        OrganizacionController.usuarioEnModel(model, request);
        camposAttributeEnModel(request, model);
        limpiarAttributesSessionMedicionesArchivo(request);
        return new ModelAndView(model, "organizacionCargarArchivoMedicion.hbs");
    }

    private void camposAttributeEnModel(Request request, HashMap<String, Object> model) {
        Object attributeIngresoValido = request.session().attribute("ingresoValido");
        Object attributeFormatoNoValido = request.session().attribute("formatoNoValido");
        Object attributeError = request.session().attribute("error");
        Object attributeSinArchivo = request.session().attribute("sinArchivo");

        boolean ingresoValidoNotNull = attributeIngresoValido != null;
        boolean formatoNoValidoNotNull = attributeFormatoNoValido != null;
        boolean errorNotNull = attributeError != null;
        boolean sinArchivoNotNull = attributeSinArchivo != null;

        if(ingresoValidoNotNull){
            model.put("ingresoValido", attributeIngresoValido);
        }
        if(formatoNoValidoNotNull){
            model.put("formatoNoValido", attributeFormatoNoValido);
        }
        if(errorNotNull){
            model.put("error", attributeError);
        }
        if(sinArchivoNotNull){
            model.put("sinArchivo", attributeSinArchivo);
        }
    }

    private void limpiarAttributesSessionMedicionesArchivo(Request request){
        request.session().removeAttribute("ingresoValido");
        request.session().removeAttribute("formatoNoValido");
        request.session().removeAttribute("error");
        request.session().removeAttribute("sinArchivo");
    }


    public ModelAndView getMedicionesPerse(Request request, Response response) {
        Map<String, Object> model = new HashMap<>();
        OrganizacionController.usuarioEnModel(model, request);
        model.put("tipoconsumos", RepoTipoDeConsumo.getInstance().getTiposConsumo());


        return new ModelAndView(model, "organizacionCargarMedicion.hbs");
    }
    public Response crearMedicion(Request request, Response response) {
        Organizacion organizacion = OrganizacionController.obtenerOrganizacion(request);

        String tipoDeConsumo = request.queryParams("tipo-consumo");
        TipoConsumo unTipoConsumo = RepoTipoDeConsumo.getInstance().getTipoConsumo(tipoDeConsumo);
        boolean tipoConsumoNull = unTipoConsumo == null;

        request.session().attribute("tipoConsumoNull",tipoConsumoNull);


        if(!tipoConsumoNull){
            String periodicidad = request.queryParams("periodicidad").toUpperCase();

            double valor = Double.parseDouble(request.queryParams("valor"));
            String fecha = request.queryParams("fecha-medicion");
            DateTimeFormatter formatoFecha = new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM")
                    .parseDefaulting(ChronoField.DAY_OF_MONTH, 15)
                    .toFormatter();
            LocalDate fechaParseada = LocalDate.parse(fecha,formatoFecha);

            TipoPerioricidad tipoPerioricidad = TipoPerioricidad.valueOf(periodicidad);

            Medicion medicion= tipoPerioricidad.buildMedicion(organizacion,unTipoConsumo,fechaParseada,valor);
            entityManager().getTransaction().begin();
            RepoMediciones.getInstance().cargarMedicion(medicion);
            entityManager().getTransaction().commit();

            request.session().attribute("exito", true);
            response.redirect("/home/mediciones/perse");
            return response;
        }
        response.redirect("/home/mediciones/perse");
        return response;
    }

    public Response subirCSVs(Request request, Response response) throws IOException, ServletException {
        Organizacion organizacion = OrganizacionController.obtenerOrganizacion(request);
        File uploadDir = new File("upload");
        uploadDir.mkdir();
        //staticFiles.externalLocation("upload");
        Path tempFile = Files.createTempFile(uploadDir.toPath(), "", ".csv");
        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        try (InputStream input = request.raw().getPart("uploaded_file").getInputStream()) {
            Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
            // Use the input stream to create a file
        }
        if (tempFile.toFile().length() == 0) {
            request.session().attribute("sinArchivo", true);
            response.redirect("/home/mediciones/archivo");
            return response;
        }
        LectorMediciones lector = new LectorMediciones(tempFile.toString(), organizacion);
        leerYCargarMediciones(request, lector);
        response.redirect("/home/mediciones/archivo");
        return response;

    }

    private void leerYCargarMediciones(Request request, LectorMediciones lector) {
        try {
            lector.leerMediciones();
            entityManager().getTransaction().begin();
            lector.cargarMediciones();
            entityManager().getTransaction().commit();
            request.session().attribute("ingresoValido", true);
        }
        catch(LaCabeceraNoTieneUnFormatoValido e){
            request.session().attribute("formatoNoValido", true);
            request.session().attribute("error", "La cabecera no tiene un formato válido");
        }
        catch (NoSeLeyeronLosCamposEsperados e){
            request.session().attribute("formatoNoValido", true);
            request.session().attribute("error", "El archivo tiene campos que no se esperaban");
        }
        catch(ElPeriodoNoConcuerdaConLaPerioricidad e){
            request.session().attribute("formatoNoValido", true);
            request.session().attribute("error", "El período no concuerda con la perioricidad");
        }
        catch(ElPeriodoDeImputacionNoEsValido e){
            request.session().attribute("formatoNoValido", true);
            request.session().attribute("error", "El período de imputación no es válido");
        }
        catch(LaPerioricidadLeidaNoEsValida e){
            request.session().attribute("formatoNoValido", true);
            request.session().attribute("error", "La periodicidad leida no es válida");
        }
        catch(ElTipoDeConsumoLeidoNoEsValido e){
            request.session().attribute("formatoNoValido", true);
            request.session().attribute("error", "El tipo de consumo no es válido, no existe o es desconocido");
        }
        catch(LaMedicionEsNegativa e){
            request.session().attribute("formatoNoValido", true);
            request.session().attribute("error", "Una/s de las mediciones tiene valor negativo");
        }
        catch(NoSeReconoceLaPeriodicidad e){
            request.session().attribute("formatoNoValido", true);
            request.session().attribute("error", "No se reconoce la periodicidad");
        }

        catch (ElPeriodoIngresadoNoEsValido e){
            request.session().attribute("formatoNoValido", true);
            request.session().attribute("error", "El período ingresado no es válido");
        }
        catch (IllegalArgumentException e){
            request.session().attribute("formatoNoValido", true);
            request.session().attribute("error", "Alguno de los campos es erróneo o no existe en el sistema");
        }


    }
}
