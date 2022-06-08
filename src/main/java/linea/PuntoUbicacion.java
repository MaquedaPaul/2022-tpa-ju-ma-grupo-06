package linea;


public abstract class PuntoUbicacion {
  private int localidadId;
  private String calle;
  private int altura;


  public PuntoUbicacion(int localidadId,String calle, int altura) {
    this.localidadId = localidadId;
    this.calle = calle;
    this.altura = altura;
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
}
