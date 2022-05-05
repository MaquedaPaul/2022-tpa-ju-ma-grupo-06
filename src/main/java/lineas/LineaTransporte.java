package lineas;

import java.util.List;

public class LineaTransporte {
  private final TipoTransporte tipoTransporte;
  private final String nombre;
  private final List<Ubicacion> ubicaciones;

  public LineaTransporte(TipoTransporte tipoTransporte,
                         String nombre, List<Ubicacion> ubicaciones) {
    this.tipoTransporte = tipoTransporte;
    this.nombre = nombre;
    this.ubicaciones = ubicaciones;
  }

  public List<Ubicacion> getParadas() {
    return ubicaciones;
  }

  public void agregarParadaAlRecorrido(Ubicacion unaParada, int posicionEnElRecorrido) {
    ubicaciones.add(posicionEnElRecorrido - 1, unaParada);
  }

  public TipoTransporte transporte() {
    return tipoTransporte;
  }

  public Ubicacion inicioDelRecorrido() {
    return ubicaciones.get(0);
  }

  public Ubicacion finalDelRecorrido() {
    return ubicaciones.get(ubicaciones.size() - 1);
  }
}
