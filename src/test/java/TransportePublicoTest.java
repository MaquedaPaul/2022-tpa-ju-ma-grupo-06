import org.junit.jupiter.api.Test;
import linea.*;
import transporte.TransportePublico;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransportePublicoTest {


  // PARADAS
  Coordenada prueba1 = new Coordenada(2,2);
  Coordenada prueba2 = new Coordenada(1,12);
  Coordenada prueba3 = new Coordenada(22,24);
  Coordenada prueba4 = new Coordenada(21,52);


  PuntoUbicacion ubicacionInicio = new PuntoUbicacion(0,"Libertador" ,200,prueba1);
  PuntoUbicacion ubicacionIntermedia = new PuntoUbicacion(1,"Callao", 1222, prueba2);
  PuntoUbicacion ubicacionFinal = new PuntoUbicacion(2,"SanJuan", 122,prueba3);
  PuntoUbicacion otraUbicacion = new PuntoUbicacion(3,"Tucuman", 222, prueba4);

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
    PuntoUbicacion ubicacionInicio = new PuntoUbicacionTransportePublico(0,"Libertador" ,200);
    PuntoUbicacion ubicacionIntermedia = new PuntoUbicacionTransportePublico(1,"Callao", 1222);
    PuntoUbicacion ubicacionFinal = new PuntoUbicacionTransportePublico(2,"SanJuan", 122);
    PuntoUbicacion otraUbicacion = new PuntoUbicacionTransportePublico(3,"Tucuman", 222);


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
