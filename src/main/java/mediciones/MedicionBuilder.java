package mediciones;

import tipo.consumo.RepoTipoDeConsumo;
import tipo.consumo.TipoConsumo;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.util.ArrayList;

import java.util.stream.Collectors;


public class MedicionBuilder {

  private BufferedReader buffer;
  private TipoConsumo tipoConsumo;
  private String perioricidad;
  private double valor;
  private String fechaMedicion;
  private String periodo;
  private int lineasLeidas = 1;

  public MedicionBuilder(String path) {
    try {
      this.buffer = new BufferedReader(new FileReader(path));
    } catch (FileNotFoundException e) {
      throw new NoSePudoAbrirElArchivo(path);
    }
  }

  void cargarMediciones() {
    String linea = this.lineaLeida();
    while(!linea.equals(null)) {
      lineasLeidas++;
      linea = this.lineaLeida();
      if(this.sigueElFormatoDeCarga(linea)) {
        this.leerParametros(linea);
        this.crearMedicion();
      } else {
        throw new ElFormatoDelArchivoNoEsValido(lineasLeidas);
      }
    }
  }

boolean sigueElFormatoDeCarga(String unaLinea) {
  List<String> lineaPartida = new ArrayList<>();
  for (String campo : unaLinea.split(",")) {
    lineaPartida.add(campo);
  }
  // tipoConsumo, valor, periodicidad (ANUAL, MENSUAL), Periodo de imputación
  if(!RepoTipoDeConsumo.tieneElTipo(lineaPartida.get(0))) {
    throw new RuntimeException("no anda");
  }

  if(!RepoTipoDeConsumo.tieneElTipo(lineaPartida.get(0))) {
    throw new RuntimeException("no anda");
  }

  if(0 > Integer.parseInt(lineaPartida.get(1))) {
    throw new RuntimeException("no anda");
  }

  try {
    perioricidad = (Perioricidad) lineaPartida.get(3));
  }

  if() {
    throw new RuntimeException("no anda");
  }


  return true;
}

  String lineaLeida() {
    try {
      return buffer.readLine();
    } catch (IOException e) {
      throw new NoSePudoLeerLaLinea(this.lineasLeidas);
    }
  }


  /*


  private final BufferedReader buffer;

  public void leerMediciones() {



  while(sePuedeLeer()) {
      leerLinea()
      if(camposCorrectos()) {
        crearMedicion()
      } else {
        romper()
      }
    }
  }

  void leerTipoConsumo() {

  }
   */
}

  /*
  public static void main(String[] args) {
    List<String> records = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader("test.csv"))) {
      String line;
      int i = 0;
      String[] values = br.lines().toList();
      while ((line = br.) != null) {

        System.out.println(values[i]);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  */
