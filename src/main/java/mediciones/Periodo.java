package mediciones;

import java.util.Arrays;
import java.util.Objects;

public enum Periodo {
  ANUAL, MENSUAL;

  public static boolean esUnPeriodoValido(String periodo) {
    return Arrays.stream(Periodo.values()).map(Enum::toString).anyMatch(unPeriodo -> Objects.equals(unPeriodo, periodo));
  }
}
