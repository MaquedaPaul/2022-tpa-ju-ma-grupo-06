import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VehiculoParticularTest {

  @Test
  public void darDeAltaUnAutoANafta() {
    TipoVehiculo esUnAuto = TipoVehiculo.AUTO;
    TipoCombustible andaANafta = TipoCombustible.NAFTA;
    VehiculoParticular autoANafta = new VehiculoParticular(TipoCombustible.NAFTA,TipoVehiculo.AUTO);

    assertEquals(autoANafta.getTipoVehiculo(),esUnAuto);
    assertEquals(autoANafta.getTipoCombustible(),andaANafta);
  }

  @Test
  public void darDeAltaUnaMotoAGasoil() {
    TipoVehiculo esUnaMoto = TipoVehiculo.MOTO;
    TipoCombustible andaAGasoil = TipoCombustible.GASOIL;
    VehiculoParticular motoAGasoil = new VehiculoParticular(TipoCombustible.GASOIL,TipoVehiculo.MOTO);

    assertEquals(motoAGasoil.getTipoVehiculo(),esUnaMoto);
    assertEquals(motoAGasoil.getTipoCombustible(),andaAGasoil);
  }
}
