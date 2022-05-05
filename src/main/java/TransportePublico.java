import Linea.*;

public class TransportePublico implements Transporte{

  private LineaTransporte lineaUtilizada;

  public TransportePublico(LineaTransporte lineaUtilizada) {
    this.lineaUtilizada = lineaUtilizada;
  }

  public LineaTransporte getLineaUtilizada() {
    return lineaUtilizada;
  }

  public TipoTransporte getTransporteInvolucrado() {
    return lineaUtilizada.transporte();
  }

  public Parada getParadaInicio() {
    return lineaUtilizada.inicioDelRecorrido();
  }

  public Parada getUltimaParada() {
    return lineaUtilizada.finalDelRecorrido();
  }
}
