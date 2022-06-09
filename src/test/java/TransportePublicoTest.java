import org.junit.jupiter.api.Test;
import linea.*;
import transporte.TransportePublico;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransportePublicoTest {


  // PARADAS
  Coordenada prueba1 = new Coordenada(2, 2);
  Coordenada prueba2 = new Coordenada(1, 12);
  Coordenada prueba3 = new Coordenada(22, 24);
  Coordenada prueba4 = new Coordenada(21, 52);


  Parada ubicacionInicio = new Parada(0, prueba1, true);
  Parada ubicacionIntermedia = new Parada(1, prueba2,true);
  Parada ubicacionFinal = new Parada(2, prueba3, true);
  Parada otraUbicacion = new Parada(3, prueba4, true);

  // LISTA DE UbicacionS
  List<Parada> paradasDeIdaDel138 = new ArrayList<>();
  List<Parada> paradasDeVueltaDel138 = new ArrayList<>();

  // LINEA TRANSPORTE
  LineaTransporte linea138 =
      new LineaTransporte(TipoTransporte.COLECTIVO, "linea138",paradasDeIdaDel138,paradasDeVueltaDel138);
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

  //REVISAR
  @Test
  public void sePuedeAgregarUnaParadaAUnaLineaExistente() {

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
