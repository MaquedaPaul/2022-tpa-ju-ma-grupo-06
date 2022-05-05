package lineas;

public class Ubicacion {
  private String nombre;
  private int posicion;

  public Ubicacion(String nombre, int posicion) {
    this.nombre = nombre;
    this.posicion = posicion;
  }

  public int getPosicion() {
    return posicion;
  }

  public String getNombre() {
    return nombre;
  }
}
