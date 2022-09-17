package admin.config;

import lombok.Getter;

@Getter
public class ValoresGlobales {

  private static ValoresGlobales instance;

  private int diasDeTrabajo = 20;

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
}
