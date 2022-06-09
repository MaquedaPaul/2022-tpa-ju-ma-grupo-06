package exceptions;

public class ElTipoLeidoNoEsValido extends RuntimeException {
  public ElTipoLeidoNoEsValido(int lineaConflictiva) {
    super("El tipo de consumo leido en la linea " + lineaConflictiva + "no existe");
  }
}
