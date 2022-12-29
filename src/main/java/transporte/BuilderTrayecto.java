package transporte;

import com.google.common.collect.Iterables;
import exceptions.NoConcuerdaInicioYFin;
import linea.PuntoUbicacion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class BuilderTrayecto {
  List<Tramo> tramos = new ArrayList<>();
  Tramo ultimoTramo = new Tramo();

  public BuilderTrayecto setPuntoOrigen(PuntoUbicacion puntoUbicacion) throws NoConcuerdaInicioYFin {
    ultimoTramo.setPuntoOrigen(puntoUbicacion);
    checkearInicioYFin();
    return this;
  }

  public BuilderTrayecto setPuntoDestino(PuntoUbicacion puntoUbicacion) {
    ultimoTramo.setPuntoDestino(puntoUbicacion);
    return this;
  }

  public BuilderTrayecto setTransporte(Transporte transporte) {
    ultimoTramo.setTransporteUtilizado(transporte);
    return this;
  }

  public void checkearInicioYFin() throws NoConcuerdaInicioYFin {
    if (!tramos.isEmpty() && !ultimoTramo.getPuntoOrigen().equals(Iterables.getLast(tramos).getPuntoDestino())) {
      throw new NoConcuerdaInicioYFin("Inicio y fin no concuerdan");
    }
  }

  public void agregarTramo() {
    Objects.requireNonNull(ultimoTramo.getPuntoOrigen());
    Objects.requireNonNull(ultimoTramo.getPuntoDestino());
    Objects.requireNonNull(ultimoTramo.getTransporteUtilizado());
    tramos.add(ultimoTramo);
    ultimoTramo = new Tramo();
  }

  public void agregarTramo(Tramo tramo) {
    Objects.requireNonNull(tramo.getPuntoOrigen());
    Objects.requireNonNull(tramo.getPuntoDestino());
    Objects.requireNonNull(tramo.getTransporteUtilizado());
    tramos.add(tramo);
  }

  public Trayecto build() {
    return new Trayecto(new HashSet<>(tramos));
  }

  public List<Tramo> getTramos() {
    return tramos;
  }

  public void eliminarUltimoTramo() {
    Tramo tramo = Iterables.getLast(tramos);
    tramos.remove(tramo);
  }
}
