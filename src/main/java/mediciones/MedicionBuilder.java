package mediciones;

import exceptions.ElPeriodoDeImputacionIngresadoNoExiste;
import exceptions.ElPeriodoDeImputacionNoEsValido;
import exceptions.ElTipoLeidoNoEsValido;
import exceptions.LaMedicionEsNegativa;
import exceptions.NoSePudoAbrirElArchivo;
import exceptions.NoSePudoLeerLaLinea;
import exceptions.NoSeReconoceLaPeriodicidad;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tipo.consumo.RepoTipoDeConsumo;
import tipo.consumo.TipoConsumo;

public class MedicionBuilder {

  private final BufferedReader buffer;
  private TipoConsumo tipoConsumo;
  private Periodo perioricidad;
  private double valor;
  private String periodoDeImputacion;
  private int lineasLeidas = 1;

  public MedicionBuilder(String path) {
    try {
      this.buffer = new BufferedReader(new FileReader(path));
    } catch (FileNotFoundException e) {
      throw new NoSePudoAbrirElArchivo(path);
    }
  }

  public void cargarMediciones() {
    String linea = this.lineaLeida();
    while (linea != null) {
      lineasLeidas++;
      linea = this.lineaLeida();
      this.validarFormatoDeCarga(linea);
      this.asignarParametros(linea);
      this.crearMedicion();
    }
  }

  private void crearMedicion() {
    RepoMediciones.getInstance()
        .cargarMedicion(new Medicion(
            this.tipoConsumo,
            this.perioricidad,
            this.valor,
            this.periodoDeImputacion));
  }

  private void asignarParametros(String linea) {
    List<String> lineaPartida = new ArrayList<>(Arrays.asList(linea.split(",")));
    this.tipoConsumo = RepoTipoDeConsumo.getInstance().getTipoDeConsumo(lineaPartida.get(0));
    this.valor = Integer.parseInt(lineaPartida.get(1));
    this.perioricidad = Periodo.valueOf(lineaPartida.get(2));
    this.periodoDeImputacion = lineaPartida.get(3);
  }

  void validarFormatoDeCarga(String unaLinea) {
    List<String> lineaPartida = new ArrayList<>(Arrays.asList(unaLinea.split(",")));

    // tipoConsumo, valor, periodicidad (ANUAL, MENSUAL), Periodo de imputación
    if (!RepoTipoDeConsumo.getInstance().existeElTipoDeConsumo(lineaPartida.get(0))) {
      throw new ElTipoLeidoNoEsValido(lineasLeidas);
    }

    if (0 > Integer.parseInt(lineaPartida.get(1))) {
      throw new LaMedicionEsNegativa(lineasLeidas);
    }

    if (Arrays.stream(Periodo.values())
        .anyMatch(elem -> Objects.equals(elem.toString(), lineaPartida.get(2)))) {
      throw new NoSeReconoceLaPeriodicidad(lineasLeidas);
    }

    if (!this.elPeriodoDeImputacionEsValido(lineaPartida.get(3))) {
      throw new ElPeriodoDeImputacionNoEsValido(lineasLeidas);
    }
  }

  boolean elPeriodoDeImputacionEsValido(String periodo) {
    String formatoAnual = "[1-9][0-9]{3}";
    String formatoMensual = "([0][1-9])|1[0-2]/".concat(formatoAnual);
    Pattern formato;
    Matcher matcher;

    switch (this.perioricidad.toString()) {
      case "ANUAL":
        formato = Pattern.compile(formatoAnual);
        matcher = formato.matcher(periodo);
        return matcher.matches();
      case "MENSUAL":
        formato = Pattern.compile(formatoMensual);
        matcher = formato.matcher(periodo);
        return matcher.matches();
      default:
        throw new ElPeriodoDeImputacionIngresadoNoExiste(lineasLeidas);
    }
  }

  String lineaLeida() {
    try {
      return buffer.readLine();
    } catch (IOException e) {
      throw new NoSePudoLeerLaLinea(this.lineasLeidas);
    }
  }
}
