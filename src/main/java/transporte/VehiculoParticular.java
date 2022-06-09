package transporte;

import linea.PuntoUbicacion;
import services.geodds.ServicioGeodds;
import services.geodds.entities.Distancia;

import java.io.IOException;

public class VehiculoParticular implements Transporte {
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
  public int distanciaEntre(PuntoUbicacion origen, PuntoUbicacion destino) throws IOException {

    ServicioGeodds servicioGeodds = ServicioGeodds.getInstancia();
    Distancia distancia = servicioGeodds.distancia(origen.getLocalidadId(), origen.getCalle(), origen.getAltura(), destino.getLocalidadId(), destino.getCalle(), destino.getAltura());

    return distancia.valor;
  }
}
