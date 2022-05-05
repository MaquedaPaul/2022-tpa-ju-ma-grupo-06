import Linea.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransportePublicoTest {

  // PARADAS
  Parada paradaInicio = new Parada("libertador",200);
  Parada paradaIntermedia = new Parada("Callao",1222);
  Parada paradaFinal = new Parada("sanJuan",122);

  // LISTA DE PARADAS
  List<Parada> paradasDel138 = Arrays.asList(paradaInicio,paradaIntermedia,paradaFinal);

  // LINEA TRANSPORTE
  LineaTransporte linea138 = new LineaTransporte(TipoTransporte.COLECTIVO,"linea138",paradasDel138);
  // COLECTIVO DE EJEMPLO
  TransportePublico unColectivo = new TransportePublico(linea138);

  @Test
  public void elTipoDeTransporteDeUnColectivoEsCOLECTIVO() {
    assertEquals(unColectivo.getTransporteInvolucrado(),TipoTransporte.COLECTIVO);
  }

  @Test
  public void elInicioDelRecorridoDelColectivoEsSuPrimerParada() {
    assertEquals(unColectivo.getParadaInicio(),paradaInicio);
  }

  @Test
  public void elFinalDelRecorridoDelColectivoEsSuUltimaParada() {
    assertEquals(unColectivo.getUltimaParada(),paradaFinal);
  }


}
