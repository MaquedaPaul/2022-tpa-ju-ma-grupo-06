package transporte;

import java.io.IOException;
import java.lang.Math;
import java.util.stream.Collectors;
import linea.LineaTransporte;
import linea.Parada;
import linea.PuntoUbicacion;
import linea.TipoTransporte;
import services.geodds.ServicioGeodds;

public class TransportePublico extends Transporte {

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
  }

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
    Parada primeraParada = this.encontrarParada(origen);
    Parada segundaParada = this.encontrarParada(destino);
    return Math.abs(primeraParada.getKmActual() - segundaParada.getKmActual());
  }

  public Parada encontrarParada(PuntoUbicacion ubicacion) {
    return this.lineaUtilizada
        .getRecorridoTotal()
        .stream()
        .filter(unaParada -> esLaMisma(ubicacion, unaParada))
        .collect(Collectors.toList())
        .get(0);
  }

  public boolean esLaMisma(PuntoUbicacion ubicacion, Parada unaParada) {
    return ubicacion.getCoordenada() == unaParada.getCoordenada();
  }
}


