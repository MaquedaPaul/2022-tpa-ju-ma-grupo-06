package exceptions;

public class ElPeriodoDeImputacionIngresadoNoExiste extends RuntimeException{
  public ElPeriodoDeImputacionIngresadoNoExiste(int lineaConflictiva) {
    super("El periodo de imputacion leido de la linea " + lineaConflictiva + " no es valido");
  }
}
