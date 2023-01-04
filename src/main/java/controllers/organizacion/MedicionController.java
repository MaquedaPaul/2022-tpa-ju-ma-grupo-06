package controllers.organizacion;

import lectorcsv.LectorMediciones;
import lectorcsv.TipoPerioricidad;
import mediciones.Medicion;
import organizacion.Organizacion;
import repositorios.RepoMediciones;
import repositorios.RepoTipoDeConsumo;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import tipoconsumo.TipoConsumo;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
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

public class MedicionController {

    public ModelAndView getMediciones(Request request, Response response) {
        Organizacion organizacion = OrganizacionController.obtenerOrganizacion(request);
        return new ModelAndView(organizacion, "organizacionRegistrarMediciones.hbs");
    }

    public ModelAndView getMedicionesArchivo(Request request, Response response) {
        Organizacion organizacion = OrganizacionController.obtenerOrganizacion(request);
        return new ModelAndView(organizacion, "organizacionCargarArchivoMedicion.hbs");
    }

    public ModelAndView getMedicionesPerse(Request request, Response response) {

        Map<String, Object> model = new HashMap<>();
        model.put("tipoconsumos", RepoTipoDeConsumo.getInstance().getTiposConsumo());


        return new ModelAndView(model, "organizacionCargarMedicion.hbs");
    }
    public ModelAndView crearMedicion(Request request, Response response) {
        Organizacion organizacion = OrganizacionController.obtenerOrganizacion(request);
        Map<String, Object> model = new HashMap<>();
        String tipoDeConsumo = request.queryParams("tipo-de-consumo");
        TipoConsumo unTipoConsumo = RepoTipoDeConsumo.getInstance().getTipoConsumo(tipoDeConsumo);
        boolean tipoConsumoNull = unTipoConsumo == null;
        model.put("tipoConsumoNull",tipoConsumoNull);
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
            RepoMediciones.getInstance().cargarMedicion(medicion);
            model.put("exito", true);
            return new ModelAndView(model,"organizacionCargarMedicion.hbs");
        }
        return new ModelAndView(model,"organizacionCargarMedicion.hbs");

    }

    public ModelAndView subirCSVs(Request request, Response response) throws IOException, ServletException {
        Organizacion organizacion = OrganizacionController.obtenerOrganizacion(request);
        Map<String, Object> model = new HashMap<>();




        File uploadDir = new File("upload");

        uploadDir.mkdir();


        //staticFiles.externalLocation("upload");
        Path tempFile = Files.createTempFile(uploadDir.toPath(), "", ".csv");


        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        try (InputStream input = request.raw().getPart("uploaded_file").getInputStream()) {
            Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
            // Use the input stream to create a file
        }
        if(tempFile.toFile().length() == 0){
            model.put("sinArchivo",true);
            return new ModelAndView(model, "organizacionCargarArchivoMedicion.hbs");
        }

        LectorMediciones lector = new LectorMediciones(tempFile.toString(),organizacion);
        try {
            lector.leerMediciones();
            lector.cargarMediciones();
            model.put("ingresoValido",true);
            return new ModelAndView(model, "organizacionCargarArchivoMedicion.hbs");
        }
        //TODO PONER MAS CATCHS y no atrapar ni lanzar excepciones genéricas
        catch (Exception e){
            model.put("formatoNoValido",true);
            return new ModelAndView(model, "organizacionCargarArchivoMedicion.hbs");
        }


    }
}
