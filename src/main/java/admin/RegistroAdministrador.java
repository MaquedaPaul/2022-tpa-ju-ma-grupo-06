package admin;

import static java.util.Objects.requireNonNull;

public class RegistroAdministrador {
  String usuario;
  String password;

  Administrador construir() {
    this.validar();
    Administrador unAdministrador = new Administrador(usuario, password);
    return unAdministrador;
  }

  private void especificarpassword(String password) {
    ValidadorPassword unValidador = new ValidadorPassword();
    unValidador.validarPassword(password);
    requireNonNull(password);
    this.password = password;
  }

  private void especificarUsuario(String usuario) {
    requireNonNull(usuario);
    this.usuario = usuario;
  }

  private void validar() {
    requireNonNull(password);
    requireNonNull(usuario);
  }
}

