public class ServicioContratado implements Transporte {
  private TipoVehiculo vehiculoContratado;

  public ServicioContratado(TipoVehiculo vehiculoContratado) {
    this.vehiculoContratado = vehiculoContratado;
  }

  public TipoVehiculo getVehiculoContratado() {
    return vehiculoContratado;
  }
}
