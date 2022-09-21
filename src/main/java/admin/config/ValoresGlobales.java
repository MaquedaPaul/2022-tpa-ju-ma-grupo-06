package admin.config;

import lombok.Getter;

@Getter
public class ValoresGlobales {

  private static ValoresGlobales instance;

  private int diasDeTrabajo = 20;
  private String formatoAnual = "\\d{4}";
  private String formatoMensual = "(0[1-9]|1[0-2])/\\d{4}";

  private ValoresGlobales() {

  }

  public static ValoresGlobales getInstance() {
    if (instance == null) {
      instance = new ValoresGlobales();
    }
    return instance;
  }

  void setDiasDeTrabajo(int dias) {
    diasDeTrabajo = dias;
  }

  void setFormatoAnual(String formato) {
    formatoAnual = formato;
  }

  void setFormatoMensual(String formato) {
    formatoMensual = formato;
  }

  public String getFormatoMensual() {
    return formatoMensual;
  }

  public String getFormatoAnual() {
    return formatoAnual;
  }
}
