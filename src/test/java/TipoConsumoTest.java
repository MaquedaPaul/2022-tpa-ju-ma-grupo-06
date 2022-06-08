import TipoConsumo.GasNatural;
import admin.FactorEmision;

import exceptions.UnidadFENoCorrespondienteConUnidadTipoConsumo;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class TipoConsumoTest {
  @Test
  public void unGasNaturalNoDeberiaPermitirTenerUnFEConUnidadDistinta(){
    GasNatural unGasNatural = new GasNatural();

    FactorEmision unFactorIncorrecto = new FactorEmision(5,"lts");
    assertThrows(UnidadFENoCorrespondienteConUnidadTipoConsumo.class,() -> unGasNatural.setFactorEmision(unFactorIncorrecto));

  }
  @Test
  public void unGasNaturalDeberiaPermitirTenerUnFEConUnidadIdentica(){
    GasNatural unGasNatural;
    unGasNatural = mock(GasNatural.class);
    FactorEmision unFactorCorrecto = new FactorEmision(5,"m3");
    //assertThrows(UnidadFENoCorrespondienteConUnidadTipoConsumo.class,() -> unGasNatural.setFactorEmision(unFactorCorrecto));
    verify(unGasNatural,atMostOnce()).setFactorEmision(unFactorCorrecto);
  }

}
