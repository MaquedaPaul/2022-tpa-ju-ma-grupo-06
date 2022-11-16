package utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Mapeador {

  public static <T> Map<String, Object> mapear(T objeto) {
    Map<String, Object> map = new HashMap<>();
    Field[] atributos = objeto.getClass().getDeclaredFields();

    for (Field atributo : atributos) {
      atributo.setAccessible(true);
      try {
        map.put(atributo.getName(), atributo.get(objeto));
      } catch (Exception ignored) {
        map.put(atributo.getName(), "No tengo el valor :C");
      }
      atributo.setAccessible(false);
    }

    return map;
  }
}
