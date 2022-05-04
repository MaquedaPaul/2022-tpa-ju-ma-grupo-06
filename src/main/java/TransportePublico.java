
public class TransportePublico implements Transporte{
  private TipoVehiculoPublico transporteInvolucrado;
  private LineaTransporte lineaUtilizada;
  private Parada paradaInicio;
  private Parada paradaDeFin;

  public TransportePublico(TipoVehiculoPublico transporteInvolucrado, LineaTransporte lineaUtilizada, Parada paradaInicio, Parada paradaDeFin) {
    this.transporteInvolucrado = transporteInvolucrado;
    this.lineaUtilizada = lineaUtilizada;
    this.paradaInicio = paradaInicio;
    this.paradaDeFin = paradaDeFin;
  }

  public LineaTransporte getLineaUtilizada() {
    return lineaUtilizada;
  }

  public TipoVehiculoPublico getTransporteInvolucrado() {
    return transporteInvolucrado;
  }

  public Parada getParadaInicio() {
    return paradaInicio;
  }

  public Parada getParadaDeFin() {
    return paradaDeFin;
  }
}
