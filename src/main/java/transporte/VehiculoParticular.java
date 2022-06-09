package transporte;

import java.io.IOException;
import linea.PuntoUbicacion;
import services.geodds.ServicioGeodds;
import services.geodds.entities.Distancia;


public class VehiculoParticular implements Transporte {
  public ServicioGeodds servicioGeodds = ServicioGeodds.getInstancia();
  private TipoVehiculo tipoVehiculo;
  private TipoCombustible tipoCombustible;

  public VehiculoParticular(TipoCombustible tipoCombustible, TipoVehiculo tipoVehiculo) {
    this.tipoCombustible = tipoCombustible;
    this.tipoVehiculo = tipoVehiculo;
  }

  public TipoVehiculo getTipoVehiculo() {
    return tipoVehiculo;
  }

  public TipoCombustible getTipoCombustible() {
    return tipoCombustible;
  }

  @Override
  public double distanciaEntre(PuntoUbicacion origen, PuntoUbicacion destino) throws IOException {

    ServicioGeodds servicioGeodds = ServicioGeodds.getInstancia();
    Distancia distancia = servicioGeodds.distancia(
        origen.getLocalidadId(),
        origen.getCalle(),
        origen.getAltura(),
        destino.getLocalidadId(),
        destino.getCalle(),
        destino.getAltura());

    return distancia.valor;
  }
}
