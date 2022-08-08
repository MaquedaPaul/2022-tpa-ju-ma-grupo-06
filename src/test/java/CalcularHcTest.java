import admin.FactorEmision;
import global.Unidad;
import linea.LineaTransporte;
import linea.Parada;
import linea.TipoTransporte;
import org.junit.jupiter.api.Test;
import tipoconsumo.TipoActividad;
import tipoconsumo.TipoAlcance;
import tipoconsumo.TipoConsumo;
import transporte.*;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CalcularHcTest {

  TipoConsumo tipoConsumoEjemplo = new TipoConsumo("text", Unidad.LTS, TipoActividad.COMBUSTION_FIJA, TipoAlcance.EMISION_DIRECTA);
  @Test
  public void unTransportePrivadoPuedeCalcularSuHuellaDeCarbono() {
    VehiculoParticular unVehiculo = new VehiculoParticular(TipoVehiculo.AUTO);
    Combustible unCombustible = new Combustible( tipoConsumoEjemplo,10);
    unVehiculo.setCombustible(unCombustible);
    FactorEmision unFactor = new FactorEmision(5, Unidad.LTS);
    unCombustible.setFactorEmision(unFactor);
    assertEquals(unVehiculo.calcularHc(), 50);
  }

    @Test
    public void unTransportePublicoPuedeCalcularSuHuellaDeCarbono() {
      // LISTA DE Ubicaciones
      List<Parada> paradasDeIdaDel138 = new ArrayList<>();
      List<Parada> paradasDeVueltaDel138 = new ArrayList<>();
      // LINEA TRANSPORTE
      LineaTransporte linea138 =
          new LineaTransporte(TipoTransporte.COLECTIVO, "linea138",paradasDeIdaDel138,paradasDeVueltaDel138);
      TransportePublico unColectivo = new TransportePublico(linea138);
      Combustible unCombustible = new Combustible(tipoConsumoEjemplo,5);
      unColectivo.setCombustible(unCombustible);
      FactorEmision unFactor = new FactorEmision(5,Unidad.LTS);
      unCombustible.setFactorEmision(unFactor);
      assertEquals(unColectivo.calcularHc(),25);
    }
  }
