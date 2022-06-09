package exceptions;

public class NoSePudoLeerLaLinea extends RuntimeException{
  public NoSePudoLeerLaLinea(int lineaConflictiva) {
    super("No se pudo leer la linea nro " + lineaConflictiva + " del archivo");
  }
}
