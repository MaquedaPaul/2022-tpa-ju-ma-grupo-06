import admin.FactorEmision;
import global.Unidad;
import org.junit.jupiter.api.Test;
import services.geodds.ServicioGeodds;
import services.geodds.entities.Distancia;
import tipoconsumo.TipoActividad;
import tipoconsumo.TipoAlcance;
import transporte.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class CalcularHcTest {

  @Test
  public void unTransportePrivadoPuedeCalcularSuHuellaDeCarbono() {
    VehiculoParticular unVehiculo = new VehiculoParticular(TipoVehiculo.AUTO);
    Combustible unCombustible = new Combustible("text", Unidad.LTS, TipoActividad.COMBUSTION_FIJA, TipoAlcance.EMISION_DIRECTA, 10);
    unVehiculo.setCombustible(unCombustible);
    FactorEmision unFAct


  }
}
