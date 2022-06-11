import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.geodds.GeoddsService;
import transporte.*;
import transporte.VehiculoParticular;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class VehiculoParticularTest {
  Factory unFactory = new Factory();
  TipoVehiculo esUnAuto = TipoVehiculo.AUTO;
  TipoCombustible andaANafta = TipoCombustible.NAFTA;
  VehiculoParticular autoANafta = new VehiculoParticular(TipoCombustible.NAFTA, TipoVehiculo.AUTO);
  ////
  TipoVehiculo esUnaMoto = TipoVehiculo.MOTO;
  TipoCombustible andaAGasoil = TipoCombustible.GASOIL;
  VehiculoParticular motoAGasoil = new VehiculoParticular(TipoCombustible.GASOIL, TipoVehiculo.MOTO);
  ////

  @BeforeEach
  void init() {
    GeoddsService geoddsService;
    geoddsService = mock(GeoddsService.class);
  }

  @Test
  public void darDeAltaUnAutoANafta() {
    assertEquals(autoANafta.getTipoVehiculo(), esUnAuto);
    assertEquals(autoANafta.getTipoCombustible(), andaANafta);
  }

  @Test
  public void darDeAltaUnaMotoAGasoil() {
    assertEquals(motoAGasoil.getTipoVehiculo(), esUnaMoto);
    assertEquals(motoAGasoil.getTipoCombustible(), andaAGasoil);
  }
  //////PREGUNTAR
  /*
  @Test
  public void huboInteraccionConLaAPIAlCalcularDistancia() throws IOException {
    Tramo unTramo = unFactory.crearTramoSimple(motoAGasoil);
    ServicioGeodds geoddsMock = mock(ServicioGeodds.class);
    motoAGasoil.servicioGeodds = geoddsMock;
    //when(geoddsMock.distancia(any(),any(),any(),any(),any(),any())).thenReturn(any());
    //geoddsMock.distancia(any(),any(),any(),any(),any(),any());
    //unTramo.distanciaTramo();
    verify(geoddsMock,times(0)).distancia(anyInt(),anyString(),anyInt(),anyInt(),anyString(),anyInt());
  }
  */

        /*
    int unaAlturaO = unTramo.getPuntoOrigen().getAltura();
    String unaCalleO = unTramo.getPuntoOrigen().getCalle();
    int unaLocalidadO = unTramo.getPuntoOrigen().getLocalidadId();
    int unaAlturaD = unTramo.getPuntoDestino().getAltura();
    String unaCalleD = unTramo.getPuntoDestino().getCalle();
    int unaLocalidadD = unTramo.getPuntoDestino().getLocalidadId();
    */
}
