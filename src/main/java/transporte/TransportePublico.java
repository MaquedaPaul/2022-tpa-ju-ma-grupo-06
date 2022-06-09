package transporte;

import linea.LineaTransporte;
import linea.TipoTransporte;
import linea.PuntoUbicacion;
import linea.Parada;
import services.geodds.ServicioGeodds;

import java.io.IOException;
import java.lang.Math;
import java.util.stream.Collectors;

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

  public PuntoUbicacion getUbicacionInicio() {
    return lineaUtilizada.inicioDelRecorrido();
  }

  public PuntoUbicacion getUltimaUbicacion() {
    return lineaUtilizada.finalDelRecorrido();
  }

  public int distanciaEntre(PuntoUbicacion origen, PuntoUbicacion destino) throws IOException {
    return origen.getKmRecorrido();
  }
}


