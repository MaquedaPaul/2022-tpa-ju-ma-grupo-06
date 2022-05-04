import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransportePublicoTest {


  @Test
  public void elTipoDeTransporteDeUnColectivoEsCOLECTIVO() {

    // LINEA TRANSPORTE
    LineaTransporte linea138 = new LineaTransporte(paradasDel138,138,TipoVehiculoPublico.COLECTIVO);

    // PARADAS
    Parada parada1 = new Parada(libertador,1);
    Parada parada2 = new Parada(sanJuan,2);

    // LISTA DE PARADAS
    List<Parada> paradasDel138 = Arrays.asList(parada1,parada2);

    // COLECTIVO DE EJEMPLO
    TransportePublico unColectivo = new TransportePublico(TipoVehiculoPublico.COLECTIVO,linea138,parada1,parada2);

    // TEST
    assertEquals(unColectivo.getTransporteInvolucrado(),TipoVehiculoPublico.COLECTIVO);
  }


}
