package admin;

public class Administrador {
  String usuario;
  String password;
  RepoFactoresEmision repoDeFactores;

  public Administrador(String unUsuario, String unPassword) {
    this.usuario = unUsuario;
    this.password = unPassword;
  }

  public String getUsuario() {
    return usuario;
  }

  public String getPassword() {
    return password;
  }

  void crearFactorEmision(double valor, String unidadDivisible) {
    repoDeFactores.incorporarFactor(new FactorEmision(valor, unidadDivisible));
  }

  public void setRepoDeFactores(RepoFactoresEmision repoDeFactores) {
    this.repoDeFactores = repoDeFactores;
  }
}


