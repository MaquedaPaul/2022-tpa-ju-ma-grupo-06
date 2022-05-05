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

  public Ubicacion getParadaInicio() {
    return lineaUtilizada.inicioDelRecorrido();
  }

  public  getUltimaParada() {
    return lineaUtilizada.finalDelRecorrido();
  }
}
