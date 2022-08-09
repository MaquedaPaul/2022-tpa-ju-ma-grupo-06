package mediciones;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import exceptions.*;
import organizacion.Organizacion;
import tipoconsumo.RepoTipoDeConsumo;
import tipoconsumo.TipoConsumo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LectorDeCsv {

  private final CSVReader reader;
  private TipoConsumo tipoConsumo;
  private Perioricidad perioricidad;
  private double valor;
  private String periodoDeImputacion;
  private final List<Medicion> mediciones = new ArrayList<>();
  private final Organizacion organizacion;

  public LectorDeCsv(String path, Organizacion organizacion) throws FileNotFoundException {
    this.reader = new CSVReader(new InputStreamReader(new FileInputStream(path),
        Charset.defaultCharset()));
    this.organizacion = organizacion;
  }

  public List<Medicion> getMediciones() {
    return mediciones;
  }


  public int getCantidadDeMediciones() {
    return mediciones.size();
  }

  public void leerMediciones() {
    String[] linea = this.lineaLeida();
    if (this.esUnaCabeceraValida(linea)) {
      linea = this.lineaLeida();
      while (linea != null) {
        this.validarFormatoLeido(linea);
        this.asignarParametros(linea);
        this.guardarMedicion();
        linea = this.lineaLeida();
      }
    } else {
      throw new LaCabeceraNoTieneUnFormatoValido();
    }
  }

  private boolean esUnaCabeceraValida(String[] cabecera) {
    ArrayList<String> cabeceraValida = new ArrayList<>();
    cabeceraValida.add("TIPOCONSUMO");
    cabeceraValida.add("VALOR");
    cabeceraValida.add("PERIORICIDAD");
    cabeceraValida.add("PERIODO DE IMPUTACION");
    return Arrays.equals(Arrays.stream(cabecera)
        .map(String::toUpperCase)
        .toArray(), cabeceraValida.toArray());
  }

  private String[] lineaLeida() {
    try {
      return reader.readNext();
    } catch (IOException | CsvValidationException e) {
      e.printStackTrace();
      throw new NoSePudoLeerLaLinea(this.lineaActual());
    }
  }

  private long lineaActual() {
    return reader.getLinesRead();
  }

  private void validarFormatoLeido(String[] campos) {
    if (campos.length != 4) {
      throw new NoSeLeyeronLosCamposEsperados(4, campos.length, this.lineaActual());
    }
    // tipoConsumo, valor, perioricidad, periodo de imputacion
    if (!RepoTipoDeConsumo.getInstance().existeElTipoDeConsumo(campos[0])) {
      throw new ElTipoDeConsumoLeidoNoEsValido(this.lineaActual());
    }
    if (Integer.parseInt(campos[1]) < 0) {
      throw new LaMedicionEsNegativa(this.lineaActual());
    }
    if (!Perioricidad.esUnPeriodoValido(campos[2])) {
      throw new LaPerioricidadLeidaNoEsValida(this.lineaActual());
    }
    if (!this.tieneElFormatoValido(campos[3], campos[2])) {
      throw new ElPeriodoNoConcuerdaConLaPerioricidad(this.lineaActual());
    }
  }

  private boolean tieneElFormatoValido(String periodoDeImputacion, String perioricidad) {
    String formatoAnual = "[0-9]{4}";
    String formatoMensual = "([0][1-9]|[1][0-2])/" + formatoAnual;
    switch (perioricidad) {
      case "ANUAL":
        return periodoDeImputacion.matches(formatoAnual);
      case "MENSUAL":
        return periodoDeImputacion.matches(formatoMensual);
      default:
        throw new LaPerioricidadLeidaNoEsValida(this.lineaActual());
    }
  }

  private void asignarParametros(String[] atributos) {
    this.tipoConsumo = RepoTipoDeConsumo.getInstance().getTipoDeConsumo(atributos[0]);
    this.valor = Integer.parseInt(atributos[1]);
    this.perioricidad = Perioricidad.valueOf(atributos[2]);
    this.periodoDeImputacion = atributos[3];
  }

  private void guardarMedicion() {
    Medicion nuevaMedicion = new Medicion(tipoConsumo,
        perioricidad,
        valor,
        periodoDeImputacion,
        organizacion);
    mediciones.add(nuevaMedicion);
  }

  public void cargarMediciones() {
    mediciones.forEach(medicion -> RepoMediciones.getInstance().cargarMedicion(medicion));
    mediciones.clear();
  }

}
