package transporte;

public class PropulsionHumana extends TransportePrivado {

  private String herramientaUtilizada;

  public PropulsionHumana(String herramientaUtilizada) {
    this.herramientaUtilizada = herramientaUtilizada;
  }

  public String getHerramientaUtilizada() {
    return herramientaUtilizada;
  }

}
