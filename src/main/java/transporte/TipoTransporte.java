package transporte;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum TipoTransporte implements WithGlobalEntityManager {
  TRANSPORTE_PUBLICO {
    @Override
    public Transporte getTransporte(Map<String, String> mapTransporte) {
      List<TransportePublico> transportes;
      String transporteUtilizado = "TransportePublico";
      String linea = mapTransporte.get("linea");
      transportes = (List<TransportePublico>) entityManager()
          .createQuery("from Transporte where TRANSPORTE_UTILIZADO = :d")
          .setParameter("d", transporteUtilizado)
          .getResultList();
      Transporte transporte = transportes.stream()
          .filter(elemento -> elemento.getLineaUtilizada().getNombre().equals(linea))
          .findFirst().orElse(null);
      return transporte;
    }
  },

  VEHICULO_PARTICULAR {
    @Override
    public Transporte getTransporte(Map<String, String> mapTransporte) {
      String tipoTransporte = mapTransporte.get("tipoTransporte");
      String nombreVehiculo = mapTransporte.get("nombreVehiculo");
      Transporte transporte = (Transporte) entityManager()
            .createQuery("from Transporte where TIPO_TRANSPORTE = :d and nombre = :nombre")
            .setParameter("d", tipoTransporte).setParameter("nombre", nombreVehiculo)
            .getResultList()
            .stream()
            .findFirst()
            .orElse(null);
      return transporte;
    }
  },

  SERCICIO_CONTRATADO {
    @Override
    public Transporte getTransporte(Map<String, String> mapTransporte) {
      String tipoTransporte = mapTransporte.get("tipoTransporte");
      Transporte transporte = (Transporte) entityManager()
          .createQuery("from Transporte where TIPO_TRANSPORTE = :d")
          .setParameter("d", tipoTransporte)
          .getResultList()
          .stream()
          .findFirst()
          .orElse(null);
      return transporte;
    }
  },

  PROPULSION_HUMANA {
    @Override
    public Transporte getTransporte(Map<String, String> mapTransporte) {
      String tipoTransporte = mapTransporte.get("tipoTransporte");
      Transporte transporte =  (Transporte) entityManager()
          .createQuery("from Transporte where TIPO_TRANSPORTE = :d")
          .setParameter("d", tipoTransporte)
          .getResultList()
          .stream()
          .findFirst()
          .orElse(null);
      return transporte;
    }
  };

  public abstract Transporte getTransporte(Map<String, String> mapTransporte);
}
