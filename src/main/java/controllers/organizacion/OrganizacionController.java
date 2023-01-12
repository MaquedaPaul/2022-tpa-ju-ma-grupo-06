package controllers.organizacion;

import com.opencsv.exceptions.CsvValidationException;
import cuenta.OrganizacionCuenta;
import lectorcsv.*;
import mediciones.Medicion;
import organizacion.periodo.PeriodoAnual;
import repositorios.*;
import miembro.Miembro;
import organizacion.Organizacion;
import organizacion.Sector;
import organizacion.Solicitud;
import organizacion.periodo.PeriodoMensual;
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
import java.util.*;

public class OrganizacionController {

  static Organizacion obtenerOrganizacion(Request request){
    OrganizacionCuenta usuario = request.session().attribute("cuenta");
    Organizacion organizacion = RepoCuentas.getInstance().obtenerOrganizacion(usuario);
    return organizacion;
  }
  static String obtenerUsuario (Request request){
    OrganizacionCuenta cuenta = request.session().attribute("cuenta");
    return cuenta.getUsuario();
  }

  public ModelAndView getCalculadoraHc(Request request, Response response) {
    Organizacion organizacion = obtenerOrganizacion(request);
    return new ModelAndView(organizacion, "organizacionCalculadoraHc.hbs");
  }

  public ModelAndView getHcTotal(Request request, Response response) {
    Organizacion organizacion = obtenerOrganizacion(request);
    int anioActual = LocalDate.now().getYear();
    //TODO
    double valor = organizacion.calcularHCTotal(new PeriodoAnual(LocalDate.of(anioActual,1, 15)));
    Map<String, Object> model = new HashMap<>();
    model.put("valorhc",valor);
    return new ModelAndView(model, "organizacionHcTotal.hbs");
  }

}
