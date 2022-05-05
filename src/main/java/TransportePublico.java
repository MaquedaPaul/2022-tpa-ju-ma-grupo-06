import lineas.LineaTransporte;
import lineas.TipoTransporte;
import lineas.Ubicacion;

public class TransportePublico implements Transporte {

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

  public Ubicacion getUbicacionInicio() {
    return lineaUtilizada.inicioDelRecorrido();
  }

  public Ubicacion getUltimaUbicacion() {
    return lineaUtilizada.finalDelRecorrido();
  }
}
