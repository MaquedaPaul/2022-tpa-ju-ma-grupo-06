import linea.Coordenada;
import linea.PuntoUbicacion;
import transporte.Tramo;
import transporte.Transporte;

public class Factory {
  Coordenada coordenadaPrueba1 = crearCoordenada(10, 170);
  Coordenada coordenadaPrueba2 = crearCoordenada(105, 110);
  Coordenada coordenadaPrueba3 = crearCoordenada(101, 120);
  Coordenada coordenadaPrueba4 = crearCoordenada(1053, 310);
  PuntoUbicacion libertador = crearPuntoUbicacion(1, "libertador", 0, coordenadaPrueba1);
  PuntoUbicacion saenz = crearPuntoUbicacion(2, "saenz", 100, coordenadaPrueba2);
  PuntoUbicacion boca = crearPuntoUbicacion(3, "boca", 200, coordenadaPrueba3);
  PuntoUbicacion sanMartin = crearPuntoUbicacion(4, "sanMartin", 300, coordenadaPrueba4);

  public Coordenada crearCoordenada(double latitud, double longitud) {
    return new Coordenada(latitud, longitud);
  }

  public PuntoUbicacion crearPuntoUbicacion(int localidadID, String unaCalle, int altura, Coordenada unaCoordenada) {
    return new PuntoUbicacion(localidadID, unaCalle, altura, unaCoordenada);
  }

  public PuntoUbicacion crearPuntoUbicacionConCoordenadas(Coordenada unaCoordenada) {
    return new PuntoUbicacion(1, "unaCalle", 100, unaCoordenada);
  }

  public Tramo crearTramoSimple(Transporte unTransporte) {
    return new Tramo(boca, libertador, unTransporte);
  }

}


