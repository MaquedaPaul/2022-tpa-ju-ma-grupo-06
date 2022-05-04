public class ServicioContratado implements Transporte {
  private String vehiculoContratado;

  public ServicioContratado(String vehiculoContratado) {
    this.vehiculoContratado = vehiculoContratado;
  }

  public String getVehiculoContratado() {
    return vehiculoContratado;
  }
}
