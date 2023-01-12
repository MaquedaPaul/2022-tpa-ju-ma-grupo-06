package transporte;

//import java.io.IOException;
//import linea.PuntoUbicacion;
//import services.geodds.ServicioGeodds;
//import services.geodds.entities.Distancia;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
public class ServicioContratado extends TransportePrivado {

  @Enumerated(EnumType.STRING)
  @Column(name = "TIPO_TRANSPORTE")
  private TipoVehiculo vehiculoContratado;

  public ServicioContratado() {
  }

  public ServicioContratado(TipoVehiculo vehiculoContratado, double consumoPorKilometro) {
    this.vehiculoContratado = vehiculoContratado;
    this.consumoPorKilometro = consumoPorKilometro;
  }

  @Override
  public TipoTransporte getTipoTransporte() {
    return TipoTransporte.SERCICIO_CONTRATADO;
  }

  @Override
  public String getDisplay() {
    return this.vehiculoContratado.toString();
  }
}
