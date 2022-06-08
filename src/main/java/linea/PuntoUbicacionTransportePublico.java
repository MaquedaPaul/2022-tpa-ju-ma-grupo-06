package linea;

public class PuntoUbicacionTransportePublico extends PuntoUbicacion{

  private int kmRecorrido;

  public PuntoUbicacionTransportePublico(int localidadId, String calle, int altura) {
    super(localidadId, calle, altura);
  }

  public int getKmRecorrido() {
    return kmRecorrido;
  }
}
