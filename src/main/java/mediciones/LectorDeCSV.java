package mediciones;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import exceptions.*;
import tipo.consumo.RepoTipoDeConsumo;
import tipo.consumo.TipoConsumo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;


public class LectorDeCSV {

  private final CSVReader reader;
  private TipoConsumo tipoConsumo;
  private Periodo perioricidad;
  private double valor;
  private String periodoDeImputacion;
  private final List<Medicion> mediciones = new ArrayList<>();

  public LectorDeCSV(String path) throws FileNotFoundException {
    this.reader = new CSVReader(new FileReader(path));
  }

  public int getCantidadDeMediciones() {
    return mediciones.size();
  }

  public void leerMediciones() {
    String[] linea = this.lineaLeida();
    while (linea != null) {
      this.validarFormatoLeido(linea);
      this.asignarParametros(linea);
      this.guardarMedicion();
      linea = this.lineaLeida();
    }
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
  private void validarFormatoLeido(String[] campos){

    // tipoConsumo, valor, perioricidad, periodo de imputacion
    if (!RepoTipoDeConsumo.getInstance().existeElTipoDeConsumo(campos[0])) {
      throw new ElTipoDeConsumoLeidoNoEsValido(this.lineaActual());
    }
    if (parseInt(campos[1]) < 0) {
      throw new LaMedicionEsNegativa(this.lineaActual());
    }
    if (!Periodo.esUnPeriodoValido(campos[2])) {
      throw new LaPerioricidadLeidaNoEsValida(this.lineaActual());
    }
    if (!this.tieneElFormatoValido(campos[3],campos[2])){
      throw new ElPeriodoNoConcuerdaConLaPerioricidad(this.lineaActual());
    }
  }

  private boolean tieneElFormatoValido(String periodoDeImputacion, String perioricidad) {
    String formatoAnual = "[0-9]{4}";
    String formatoMensual = "[0][1-9]|[1][0-2]" + formatoAnual;
    switch (perioricidad) {
      case "ANUAL":
        return periodoDeImputacion.matches(formatoAnual);
      case "MENSUAL":
        return periodoDeImputacion.matches(formatoMensual);
      default:
        throw new ElPeriodoDeImputacionNoEsValido(this.lineaActual());
    }
  }

  private void asignarParametros(String[] atributos) {
    this.tipoConsumo = RepoTipoDeConsumo.getInstance().getTipoDeConsumo(atributos[0]);
    this.valor = parseInt(atributos[1]);
    this.perioricidad = Periodo.valueOf(atributos[2]);
    this.periodoDeImputacion = atributos[3];
  }

  private void guardarMedicion() {
    Medicion nuevaMedicion = new Medicion(tipoConsumo,perioricidad,valor,periodoDeImputacion);
    mediciones.add(nuevaMedicion);
  }

  public void cargarMediciones() {
    mediciones.forEach(medicion -> RepoMediciones.getInstance().cargarMedicion(medicion));
    mediciones.clear();
  }

}
