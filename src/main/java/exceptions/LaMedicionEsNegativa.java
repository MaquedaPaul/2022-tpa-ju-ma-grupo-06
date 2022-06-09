package exceptions;

public class LaMedicionEsNegativa extends RuntimeException{
  public LaMedicionEsNegativa(int lineaConflictiva) {
    super("La medicion es negativa en la linea " + lineaConflictiva);
  }
}
