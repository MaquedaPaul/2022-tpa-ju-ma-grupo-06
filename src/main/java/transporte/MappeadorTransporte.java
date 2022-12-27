package transporte;

import java.util.HashMap;
import java.util.Map;

public enum MappeadorTransporte {
  TRANSPORTE_PUBLICO {
    @Override
    public Map<String, String> mapearTransporte(String[] queryParams) {
      Map<String, String> map = super.mapearTransporte(queryParams);
      map.put("linea", queryParams[2]);
      return map;
    }
  },

  PROPULSION_HUMANA,

  VEHICULO_PARTICULAR {
    @Override
    public Map<String, String> mapearTransporte(String[] queryParams) {
      Map<String, String> map = super.mapearTransporte(queryParams);
      StringBuilder nombreVehiculo = new StringBuilder();
      for (int i = 2; i < queryParams.length; i++) {
        nombreVehiculo.append(queryParams[i]).append(" ");
      }
      map.put("nombreVehiculo", nombreVehiculo.toString().replaceFirst("\\s++$", ""));
      return map;
    }
  },

  SERCICIO_CONTRATADO;

  public Map<String, String> mapearTransporte(String[] queryParams) {
    Map<String, String> map = new HashMap<>();
    map.put("tipoTransporte", queryParams[1]);
    return map;
  };
}
