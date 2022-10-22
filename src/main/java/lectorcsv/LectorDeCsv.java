package lectorcsv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import exceptions.*;
import lombok.Getter;
import mediciones.Medicion;
import mediciones.RepoMediciones;
import organizacion.Organizacion;
import tipoconsumo.RepoTipoDeConsumo;
import tipoconsumo.TipoConsumo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class LectorDeCsv {

  private final CSVReader reader;
  private final Organizacion organizacion;
  private final FormatoDeFechas formatoDeFechas;
  private final ValidadorDeCabeceras cabeceraEsperada;

  private TipoConsumo tipoConsumo;
  private Perioricidad perioricidad;
  private double valor;

  private final List<Medicion> mediciones = new ArrayList<>();


  public LectorDeCsv(String path, Organizacion organizacion, FormatoDeFechas formato, ValidadorDeCabeceras cabeceraEsperada) throws IOException {
    this.reader = new CSVReader(new InputStreamReader(Files.newInputStream(Paths.get(path)),
        Charset.defaultCharset()));
    this.organizacion = organizacion;
    this.formatoDeFechas = formato;
    this.cabeceraEsperada = cabeceraEsperada;
  }

  public int getCantidadDeMediciones() {
    return mediciones.size();
  }

  public void leerMediciones() {
    List<String> linea = this.lineaLeida();
    if (!this.esUnaCabeceraValida(linea)) {
      throw new LaCabeceraNoTieneUnFormatoValido();
    }
    linea = this.lineaLeida();
    while (linea != null) {
      this.validarFormatoLeido(linea);
      this.asignarParametros(linea);
      this.guardarMedicion();
      linea = this.lineaLeida();
    }
  }

  private boolean esUnaCabeceraValida(List<String> cabecera) {
    return cabeceraEsperada.validar(cabecera);
  }

  private List<String> lineaLeida() {
    try {
      return Arrays.asList(reader.readNext());
    } catch (IOException | CsvValidationException e) {
      e.printStackTrace();
      throw new NoSePudoLeerLaLinea(this.lineaActual());
    }
  }

  private long lineaActual() {
    return reader.getLinesRead();
  }

  private void validarFormatoLeido(List<String> campos) {

    this.tieneLaCantidadCorrectaDeColumnas(campos);
    this.esUnTipoDeConsumoValido(campos.get(0));
    this.elValorLeidoEsPositivo(Integer.parseInt(campos.get(1)));
    this.esUnaPerioricidadValida(campos.get(2));
    this.tieneElFormatoValido(campos.get(3), TipoPerioricidad.valueOf(campos.get(2)));

  }

  private void tieneLaCantidadCorrectaDeColumnas(List<String> columnas) {
    if (columnas.size() != cabeceraEsperada.getCantidadDeColumnas()) {
      throw new NoSeLeyeronLosCamposEsperados(cabeceraEsperada.getCantidadDeColumnas(), columnas.size(), this.lineaActual());
    }
  }

  private void esUnTipoDeConsumoValido(String tipoConsumo) {
    if (!RepoTipoDeConsumo.getInstance().existeElTipoDeConsumo(tipoConsumo)) {
      throw new ElTipoDeConsumoLeidoNoEsValido(this.lineaActual());
    }
  }

  private void elValorLeidoEsPositivo(int valor) {
    if (valor <= 0) {
      throw new LaMedicionEsNegativa(this.lineaActual());
    }
  }

  private void esUnaPerioricidadValida(String perioricidad) {
    try {
      TipoPerioricidad.valueOf(perioricidad);
    } catch (IllegalArgumentException e) {
      throw new LaPerioricidadLeidaNoEsValida(this.lineaActual());
    }
  }

  private void tieneElFormatoValido(String periodoDeImputacion, TipoPerioricidad perioricidad) {

    if (!formatoDeFechas.tieneElFormatoValido(periodoDeImputacion, perioricidad)) {
      throw new ElPeriodoNoConcuerdaConLaPerioricidad(this.lineaActual());
    }
  }

  private void asignarParametros(List<String> atributos) {
    this.tipoConsumo = RepoTipoDeConsumo.getInstance().getTipoConsumo(atributos.get(0));
    this.perioricidad = new Perioricidad(atributos.get(2), TipoPerioricidad.valueOf(atributos.get(3)));
    this.valor = Integer.parseInt(atributos.get(1));
  }

  private void guardarMedicion() {
    Medicion nuevaMedicion = new Medicion(tipoConsumo,
        perioricidad,
        valor,
        organizacion);
    mediciones.add(nuevaMedicion);
  }

  public void cargarMediciones() {
    mediciones.forEach(medicion -> RepoMediciones.getInstance().cargarMedicion(medicion));
    mediciones.clear();
  }

}
