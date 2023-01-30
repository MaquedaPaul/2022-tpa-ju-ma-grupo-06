package cuenta;

import java.util.Arrays;

public enum TipoCuenta {
  MIEMBRO() {
    @Override
    public boolean puedeAccederA(String path) {
      String[] pathsValidos = {
          ".*/",
          ".*/home",
          ".*/recomendaciones",
          ".*/home/trayectos",
          ".*/home/trayectos/nuevo-trayecto",
          ".*/home/trayectos/nuevo-trayecto/tramos",
          ".*/home/trayectos/nuevo-trayecto/nuevo-tramo/transporte",
          ".*/home/trayectos/nuevo-trayecto/nuevo-tramo/recorrido",
          ".*/home/trayectos/nuevo-trayecto/nuevo-tramo/transporte/paradas",
          ".*/home/trayectos/nuevo-trayecto/nuevo-tramo/datos-tramo",
          ".*/home/trayectos/nuevo-trayecto/nuevo-tramo",
          ".*/home/trayectos/nuevo-trayecto/nuevo-tramo/eliminar",
          ".*/home/trayectos/nuevo-trayecto/nuevo-tramo/transporte",
          ".*/home/vinculacion",
          ".*/home/trayectos/nuevo-trayecto/nuevo-tramo/puntos-ubicacion",
          ".*/home/trayectos/nuevo-trayecto/nuevo-tramo/datos-tramo",
          ".*/home/trayectos/nuevo-trayecto/nuevo-tramo/eliminar",
          ".*/home/trayectos/nuevo-trayecto/borrar-todo",
          ".*/not-found"
      };
      return Arrays.stream(pathsValidos).anyMatch(path::matches);
    }
  }, ORGANIZACION() {
    @Override
    public boolean puedeAccederA(String path) {
      String[] pathsValidos = {
          ".*/",
          ".*/home",
          ".*/home/vinculaciones",
          ".*/home/vinculaciones/.*/aceptar",
          ".*/home/vinculaciones/.*/rechazar",
          ".*/home/mediciones",
          ".*/home/mediciones/perse",
          ".*/home/mediciones/perse/creado",
          ".*/home/mediciones/archivo",
          ".*/home/calculadora-hc",
          ".*/home/calculadora-hc/hc-total",
          ".*/home/calculadora-hc/impacto-de-miembro/buscador",
          ".*/home/calculadora-hc/impacto-de-miembro",
          ".*/home/calculadora-hc/impacto-de-miembro/.*",
          ".*/home/calculadora-hc/indicador-hc-sector/buscador",
          ".*/home/calculadora-hc/indicador-hc-sector",
          ".*/home/calculadora-hc/indicador-hc-sector/.*",
          ".*/not-found"
      };
      return Arrays.stream(pathsValidos).anyMatch(path::matches);
    }
  }, AGENTE() {
    @Override
    public boolean puedeAccederA(String path) {
      String[] pathsValidos = {
          ".*/",
          ".*/home",
          ".*/home/composicion-hc",
          ".*/home/composicion-hc/grafico",
          ".*/home/evolucion-hc",
          ".*/home/evolucion-hc/grafico",
          ".*/home/organizaciones",
          ".*/home/hc-total",
          ".*/home/organizaciones/hc-tipo-organizacion/.*",
          ".*/home/organizaciones/.*/evolucion-hc/consulta",
          ".*/home/organizaciones/.*/evolucion-hc/grafico",
          ".*/home/organizaciones/.*/composicion-hc/consulta",
          ".*/home/organizaciones/.*/composicion-hc/grafico",
          ".*/not-found"
      };
      return Arrays.stream(pathsValidos).anyMatch(path::matches);
    }
  };

  public abstract boolean puedeAccederA(String path);
}
