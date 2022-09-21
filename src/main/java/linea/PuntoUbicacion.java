package linea;


public class PuntoUbicacion {
  private int localidadId;
  private String calle;
  private int altura;
  private Coordenada coordenada;


  public PuntoUbicacion(int localidadId, String calle, int altura, Coordenada coordenada) {
    this.localidadId = localidadId;
    this.calle = calle;
    this.altura = altura;
    this.coordenada = coordenada;
  }

  public int getLocalidadId() {
    return localidadId;
  }

  public String getCalle() {
    return calle;
  }

  public int getAltura() {
    return altura;
  }

  public Coordenada getCoordenada() {
    return coordenada;
  }
}
