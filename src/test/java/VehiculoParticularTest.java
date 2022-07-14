import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.geodds.GeoddsService;
import services.geodds.ServicioGeodds;
import services.geodds.entities.Distancia;
import transporte.*;
import transporte.VehiculoParticular;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class VehiculoParticularTest {
  Factory unFactory = new Factory();

  @BeforeEach
  void init() {
    GeoddsService geoddsService;
    geoddsService = mock(GeoddsService.class);
  }

  @Test
  public void darDeAltaUnAutoANafta() {
    TipoVehiculo esUnAuto = TipoVehiculo.AUTO;
    TipoCombustible andaANafta = TipoCombustible.NAFTA;
    VehiculoParticular autoANafta = new VehiculoParticular(TipoCombustible.NAFTA, TipoVehiculo.AUTO);
    autoANafta.getTipoVehiculo().equals(esUnAuto);
    assertEquals(autoANafta.getTipoVehiculo(), esUnAuto);
    assertEquals(autoANafta.getTipoCombustible(), andaANafta);
  }

  @Test
  public void darDeAltaUnaMotoAGasoil() {
    TipoVehiculo esUnaMoto = TipoVehiculo.MOTO;
    TipoCombustible andaAGasoil = TipoCombustible.GASOIL;
    VehiculoParticular motoAGasoil = new VehiculoParticular(andaAGasoil, esUnaMoto);
    assertEquals(motoAGasoil.getTipoVehiculo(), esUnaMoto);
    assertEquals(motoAGasoil.getTipoCombustible(), andaAGasoil);
  }

  @Test
  public void huboInteraccionConLaAPIAlCalcularDistancia() throws IOException {
    TipoVehiculo esUnaMoto = TipoVehiculo.MOTO;
    TipoCombustible andaAGasoil = TipoCombustible.GASOIL;
    VehiculoParticular motoAGasoil = new VehiculoParticular(andaAGasoil, esUnaMoto);
    Tramo unTramo = unFactory.crearTramoSimple(motoAGasoil);
    ServicioGeodds geoddsMock = mock(ServicioGeodds.class);
    motoAGasoil.setServiocioGeo(geoddsMock);
    when(geoddsMock.distancia(anyInt(), any(), anyInt(), anyInt(), any(), anyInt())).thenReturn(new Distancia(5));
    unTramo.distanciaTramo();
    verify(geoddsMock, times(1)).distancia(anyInt(), anyString(), anyInt(), anyInt(), anyString(), anyInt());
  }
}
