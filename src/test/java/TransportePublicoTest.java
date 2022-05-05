import org.junit.jupiter.api.Test;
import linea.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransportePublicoTest {

  // PARADAS
  Ubicacion ubicacionInicio = new Ubicacion("Libertador", 200);
  Ubicacion ubicacionIntermedia = new Ubicacion("Callao", 1222);
  Ubicacion ubicacionFinal = new Ubicacion("SanJuan", 122);
  Ubicacion otraUbicacion = new Ubicacion("Tucuman", 222);

  // LISTA DE UbicacionS
  List<Ubicacion> ubicacionesDel138 = new ArrayList<>();

  // LINEA TRANSPORTE
  LineaTransporte linea138 =
      new LineaTransporte(TipoTransporte.COLECTIVO, "linea138", ubicacionesDel138);
  // COLECTIVO DE EJEMPLO
  TransportePublico unColectivo = new TransportePublico(linea138);

  @Test
  public void elTipoDeTransporteDeUnColectivoEsCOLECTIVO() {
    assertEquals(unColectivo.getTransporteInvolucrado(), TipoTransporte.COLECTIVO);
  }

  @Test
  public void elInicioDelRecorridoDelColectivoEsSuPrimerUbicacion() {
    ubicacionesDel138.add(ubicacionInicio);
    ubicacionesDel138.add(ubicacionIntermedia);
    ubicacionesDel138.add(ubicacionFinal);

    assertEquals(unColectivo.getUbicacionInicio(), ubicacionInicio);
  }

  @Test
  public void elFinalDelRecorridoDelColectivoEsSuUltimaUbicacion() {
    ubicacionesDel138.add(ubicacionInicio);
    ubicacionesDel138.add(ubicacionIntermedia);
    ubicacionesDel138.add(ubicacionFinal);

    assertEquals(unColectivo.getUltimaUbicacion(), ubicacionFinal);
  }

  @Test
  public void sePuedeAgregarUnaParadaAUnaLineaExistente() {
    ubicacionesDel138.add(ubicacionInicio);
    ubicacionesDel138.add(ubicacionIntermedia);
    ubicacionesDel138.add(ubicacionFinal);

    assertEquals(linea138.getParadas().size(), 3);
    linea138.agregarParadaAlRecorrido(otraUbicacion, 3);
    assertEquals(linea138.getParadas().size(), 4);
  }

}
