import admin.FactorEmision;
import global.Unidad;
import linea.LineaTransporte;
import linea.Parada;
import linea.TipoTransporte;
import org.junit.jupiter.api.Test;
import services.geodds.ServicioGeodds;
import services.geodds.entities.Distancia;
import tipoconsumo.TipoActividad;
import tipoconsumo.TipoAlcance;
import transporte.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    FactorEmision unFactor = new FactorEmision(5, Unidad.LTS);
    unCombustible.setFactorEmision(unFactor);
    assertEquals(unVehiculo.calcularHc(), 50);
  }

    @Test
    public void unTransportePublicoPuedeCalcularSuHuellaDeCarbono() {
      // LISTA DE UbicacionS
      List<Parada> paradasDeIdaDel138 = new ArrayList<>();
      List<Parada> paradasDeVueltaDel138 = new ArrayList<>();
      // LINEA TRANSPORTE
      LineaTransporte linea138 =
          new LineaTransporte(TipoTransporte.COLECTIVO, "linea138",paradasDeIdaDel138,paradasDeVueltaDel138);

      TransportePublico unColectivo = new TransportePublico(linea138);
      Combustible unCombustible = new Combustible("text", Unidad.LTS, TipoActividad.COMBUSTION_FIJA,TipoAlcance.EMISION_DIRECTA,5);
      unColectivo.setCombustible(unCombustible);
      FactorEmision unFactor = new FactorEmision(5,Unidad.LTS);
      unCombustible.setFactorEmision(unFactor);
      assertEquals(unColectivo.calcularHc(),25);
    }
  }
