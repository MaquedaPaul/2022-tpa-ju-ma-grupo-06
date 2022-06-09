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
    paradasDeIdaDel138.add(ubicacionInicio);
    paradasDeIdaDel138.add(ubicacionIntermedia);
    paradasDeIdaDel138.add(ubicacionFinal);

    assertEquals(unColectivo.getUbicacionInicioPrimerRecorrido(), ubicacionInicio);
  }

  @Test
  public void elFinalDelRecorridoDelColectivoEsSuUltimaUbicacion() {
    paradasDeIdaDel138.add(ubicacionInicio);
    paradasDeIdaDel138.add(ubicacionIntermedia);
    paradasDeIdaDel138.add(ubicacionFinal);

    assertEquals(unColectivo.getUltimaUbicacionPrimerRecorrido(), ubicacionFinal);
  }

  //REVISAR
  @Test
  public void sePuedeAgregarUnaParadaAUnaLineaExistente() {

    LineaTransporte linea138 =
        new LineaTransporte(TipoTransporte.COLECTIVO, "linea138",paradasDeIdaDel138,paradasDeVueltaDel138);
    paradasDeIdaDel138.add(ubicacionInicio);
    paradasDeIdaDel138.add(ubicacionIntermedia);
    paradasDeIdaDel138.add(ubicacionFinal);

    assertEquals(linea138.getRecorridoDeIda().size(), 3);
    linea138.agregarParadaAlRecorrido(otraUbicacion);
    assertEquals(linea138.getRecorridoDeIda().size(), 4);
  }

  @Test
  public void sePuedeCalcularLaDistanciaEntre2Paradas() throws IOException {
    LineaTransporte linea138 =
        new LineaTransporte(TipoTransporte.COLECTIVO, "linea138",paradasDeIdaDel138,paradasDeVueltaDel138);

    paradasDeIdaDel138.add(ubicacionInicio);
    paradasDeIdaDel138.add(ubicacionIntermedia);
    paradasDeIdaDel138.add(ubicacionFinal);
    
    PuntoUbicacion paradaInicio = new PuntoUbicacion(32,"aa",32,prueba1);
    PuntoUbicacion paradaFinal = new PuntoUbicacion(33,"bb",33,prueba3);

    TransportePublico bondi138 = new TransportePublico(linea138);

    assertEquals(bondi138.distanciaEntre(paradaInicio, paradaFinal),2);

  }
}
