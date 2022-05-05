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
}
