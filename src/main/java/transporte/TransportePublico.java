package transporte;

import linea.LineaTransporte;
import linea.Parada;
import linea.PuntoUbicacion;
import linea.TipoTransporte;
import lombok.Getter;

import javax.persistence.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@DiscriminatorValue("TRANSPORTE PUBLICO")

public class TransportePublico extends Transporte {

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "ID_LINEA")
  private LineaTransporte lineaUtilizada;

  protected TransportePublico() {
  }

  public TransportePublico(LineaTransporte lineaUtilizada, double consumoPorKilometro) {
    this.lineaUtilizada = lineaUtilizada;
    setConsumoPorKilometro(consumoPorKilometro);
    setNombre("COLECTIVO " + getLineaUtilizada().getNombre());
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
        .filter(unaParada -> ubicacion.equals(unaParada.getPuntoUbicacion()))
        .collect(Collectors.toList())
        .get(0);
  }

  /**
   * Retorna el PuntoUbicacion de la parada del <strong>km</strong> en <strong>sentido</strong> del recorrido dado
   * @param km
   * @param sentido
   * @return PuntoUbicacion pUbicacion
   */
  public PuntoUbicacion getPuntoEnKm(int km,String sentido) {

    return this.getRecorridoSegun(sentido)
            .stream()
            .filter(p -> p.getKmActual() == km).collect(Collectors.toList())
            .get(0)
            .getPuntoUbicacion();
  }

  private List<Parada> getRecorridoSegun(String sentido) {

    List<Parada> recorrido;
    switch (sentido) {
      case "IDA": return this.lineaUtilizada.getRecorridoDeIda();
      case "VUELTA": return this.lineaUtilizada.getRecorridoVuelta();
      default: throw new RuntimeException("EL SENTIDO NO EXISTE");
    }
  }
  public boolean tieneUnaParadaElPunto(PuntoUbicacion punto, String sentido) {

    return this.getRecorridoSegun(sentido)
            .stream()
            .anyMatch(p -> p.getPuntoUbicacion().equals(punto));
  }

  public int getKmEnPunto(PuntoUbicacion punto, String sentido) {
    return this.getRecorridoSegun(sentido)
            .stream()
            .filter(parada -> parada.getPuntoUbicacion().equals(punto))
            .collect(Collectors.toList())
            .get(0).getKmActual();
  }

}


