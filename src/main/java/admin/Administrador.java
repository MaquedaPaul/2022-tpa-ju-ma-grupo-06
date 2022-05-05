package admin;

public class Administrador {
  String usuario;
  String password;

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
}


