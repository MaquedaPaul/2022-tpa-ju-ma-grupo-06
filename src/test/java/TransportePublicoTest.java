import Linea.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransportePublicoTest {

  // PARADAS
  Ubicacion UbicacionInicio = new Ubicacion("libertador",200);
  Ubicacion UbicacionIntermedia = new Ubicacion("Callao",1222);
  Ubicacion UbicacionFinal = new Ubicacion("sanJuan",122);

  // LISTA DE UbicacionS
  List<Ubicacion> UbicacionesDel138 = Arrays.asList(UbicacionInicio,UbicacionIntermedia,UbicacionFinal);

  // LINEA TRANSPORTE
  LineaTransporte linea138 = new LineaTransporte(TipoTransporte.COLECTIVO,"linea138",UbicacionesDel138);
  // COLECTIVO DE EJEMPLO
  TransportePublico unColectivo = new TransportePublico(linea138);

  @Test
  public void elTipoDeTransporteDeUnColectivoEsCOLECTIVO() {
    assertEquals(unColectivo.getTransporteInvolucrado(),TipoTransporte.COLECTIVO);
  }

  @Test
  public void elInicioDelRecorridoDelColectivoEsSuPrimerUbicacion() {
    assertEquals(unColectivo.getUbicacionInicio(),UbicacionInicio);
  }

  @Test
  public void elFinalDelRecorridoDelColectivoEsSuUltimaUbicacion() {
    assertEquals(unColectivo.getUltimaUbicacion(),UbicacionFinal);
  }


}
