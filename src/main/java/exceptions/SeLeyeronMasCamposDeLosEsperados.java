package exceptions;

public class SeLeyeronMasCamposDeLosEsperados extends RuntimeException{
  public SeLeyeronMasCamposDeLosEsperados(int camposEsperados,int camposLeidos,long lineaActual) {
    super("Se leyeron mas campos de los esperados en la linea " + lineaActual +", esperados: " + camposEsperados + ", leidos: " + camposLeidos);
  }
}
