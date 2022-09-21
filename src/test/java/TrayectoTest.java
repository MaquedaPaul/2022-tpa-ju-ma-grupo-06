import linea.Coordenada;
import linea.PuntoUbicacion;
import org.junit.jupiter.api.Test;
import transporte.PropulsionHumana;
import transporte.Tramo;
import transporte.Trayecto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrayectoTest {
  Coordenada unaCoordenada = new Coordenada(50, 21);
  Coordenada otraCoordenada = new Coordenada(510, 221);
  PuntoUbicacion origen = new PuntoUbicacion(0, "mercedes", 23, unaCoordenada);
  PuntoUbicacion destino = new PuntoUbicacion(35, "libertador", 232, otraCoordenada);
  ;
  PropulsionHumana bicicleta = new PropulsionHumana("Bicicleta");
  Tramo unTramo = new Tramo(origen, destino, bicicleta);

  @Test
  void unTramoAgregadoDeberiaAparecerEnLaListaTramos() {
    List<Tramo> unosTramos = new ArrayList<>();
    Trayecto unTrayecto = new Trayecto(unosTramos);
    assertFalse(unosTramos.contains(unTramo));
    unTrayecto.agregarTramo(unTramo);
    assertTrue(unosTramos.contains(unTramo));
  }

  @Test
  void laDistanciaTotalDeUnTrayectoEsLaSumaDeSusTramos() {

    Tramo unTramoMock = mock(Tramo.class);
    Tramo otroTramoMock = mock(Tramo.class);

    when(unTramoMock.distanciaTramo()).thenReturn(100.0);
    when(otroTramoMock.distanciaTramo()).thenReturn(50.0);
    Trayecto unTrayecto = new Trayecto(new ArrayList<>());
    unTrayecto.agregarTramo(unTramoMock);
    unTrayecto.agregarTramo(otroTramoMock);
    unTramoMock.distanciaTramo();
    otroTramoMock.distanciaTramo();
    assertEquals(unTrayecto.distanciaTotal(), 150);

  }


}
