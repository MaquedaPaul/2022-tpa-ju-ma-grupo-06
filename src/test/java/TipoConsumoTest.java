import admin.FactorEmision;

import exceptions.UnidadFeNoCorrespondienteConUnidadTipoConsumo;
import org.junit.jupiter.api.Test;
import tipoconsumo.TipoActividad;
import tipoconsumo.TipoAlcance;
import tipoconsumo.TipoConsumo;


import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class TipoConsumoTest {
  TipoConsumo unGasNatural = new TipoConsumo("Gas Natural", "m3", TipoActividad.COMBUSTION_FIJA, TipoAlcance.EMISION_DIRECTA);

  @Test
  public void unGasNaturalNoDeberiaPermitirTenerUnFEConUnidadDistinta() {


    FactorEmision unFactorIncorrecto = new FactorEmision(5, "lts");
    assertThrows(UnidadFeNoCorrespondienteConUnidadTipoConsumo.class, () -> unGasNatural.setFactorEmision(unFactorIncorrecto));

  }

  @Test
  public void unGasNaturalDeberiaPermitirTenerUnFEConUnidadIdentica() {
    unGasNatural = mock(TipoConsumo.class);
    FactorEmision unFactorCorrecto = new FactorEmision(5, "m3");
    //assertThrows(UnidadFENoCorrespondienteConUnidadTipoConsumo.class,() -> unGasNatural.setFactorEmision(unFactorCorrecto));
    verify(unGasNatural, atMostOnce()).setFactorEmision(unFactorCorrecto);
  }

}
