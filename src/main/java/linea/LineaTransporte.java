package linea;

import java.util.List;

public class LineaTransporte {
  private TipoTransporte tipoTransporte;
  private String nombre;
  private List<PuntoUbicacion> ubicaciones;

  public LineaTransporte(TipoTransporte tipoTransporte, String nombre,
      List<PuntoUbicacion> ubicaciones) {
    this.tipoTransporte = tipoTransporte;
    this.nombre = nombre;
    this.ubicaciones = ubicaciones;
  }

  public List<PuntoUbicacion> getParadas() {
    return ubicaciones;
  }

  /**
   * Toma unaParada y la agrega en la posicionEnElRecorrido indicada.
   * 
   * @param unaParada
   * @param posicionEnElRecorrido
   */
  public void agregarParadaAlRecorrido(PuntoUbicacion unaParada, int posicionEnElRecorrido) {
    ubicaciones.add(posicionEnElRecorrido - 1, unaParada);
  }

  public TipoTransporte transporte() {
    return tipoTransporte;
  }

  public PuntoUbicacion inicioDelRecorrido() {
    return ubicaciones.get(0);
  }

  public PuntoUbicacion finalDelRecorrido() {
    return ubicaciones.get(ubicaciones.size() - 1);
  }

  public String getNombre() {
    return nombre;
  }
}
