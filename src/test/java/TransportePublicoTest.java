import org.junit.jupiter.api.Test;
import linea.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransportePublicoTest {


  // PARADAS
  PuntoUbicacion ubicacionInicio = new PuntoUbicacion("Libertador", 200);
  PuntoUbicacion ubicacionIntermedia = new PuntoUbicacion("Callao", 1222);
  PuntoUbicacion ubicacionFinal = new PuntoUbicacion("SanJuan", 122);
  PuntoUbicacion otraUbicacion = new PuntoUbicacion("Tucuman", 222);

  // LISTA DE UbicacionS
  List<PuntoUbicacion> ubicacionesDel138 = new ArrayList<>();

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
    PuntoUbicacion ubicacionInicio = new PuntoUbicacion("Libertador", 200);
    PuntoUbicacion ubicacionIntermedia = new PuntoUbicacion("Callao", 1222);
    PuntoUbicacion ubicacionFinal = new PuntoUbicacion("SanJuan", 122);
    PuntoUbicacion otraUbicacion = new PuntoUbicacion("Tucuman", 222);

    LineaTransporte linea138 =
        new LineaTransporte(TipoTransporte.COLECTIVO, "linea138", ubicacionesDel138);
    ubicacionesDel138.add(ubicacionInicio);
    ubicacionesDel138.add(ubicacionIntermedia);
    ubicacionesDel138.add(ubicacionFinal);

    assertEquals(linea138.getParadas().size(), 3);
    linea138.agregarParadaAlRecorrido(otraUbicacion, 3);
    assertEquals(linea138.getParadas().size(), 4);
  }

}
