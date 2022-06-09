package transporte;

import java.io.IOException;
import java.lang.Math;
import java.util.stream.Collectors;
import linea.LineaTransporte;
import linea.Parada;
import linea.PuntoUbicacion;
import linea.TipoTransporte;
import services.geodds.ServicioGeodds;

public class TransportePublico implements Transporte {
  public ServicioGeodds servicioGeodds = ServicioGeodds.getInstancia();

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

  public Parada getUbicacionInicioPrimerRecorrido() {
    return lineaUtilizada.inicioDelRecorridoDeIda();
  } // ARREGLAR

  public Parada getUltimaUbicacionPrimerRecorrido() {
    return lineaUtilizada.finalDelRecorridoDeIda();
  }

  public Parada getUltimaUbicacionRecorridoVuelta() {
    return lineaUtilizada.finalDelRecorridoDeRegreso();
  }

  public Parada getPrimeraUbicacionRecorridoVuelta() {
    return lineaUtilizada.inicioDelRecorridoDeRegreso();
  }

  public double distanciaEntre(PuntoUbicacion origen, PuntoUbicacion destino) throws IOException {
    Parada parada1 = this.encontrarParada(origen);
    Parada parada2 = this.encontrarParada(destino);
    return Math.abs(parada1.getKmActual() - parada2.getKmActual());
  }

  public Parada encontrarParada(PuntoUbicacion ubicacion) {
    return this.lineaUtilizada
        .getRecorridoTotal()
        .stream()
        .filter(unaParada -> ubicacion.getCoordenada() == unaParada.getCoordenada())
        .collect(Collectors.toList())
        .get(0);
  }
}


