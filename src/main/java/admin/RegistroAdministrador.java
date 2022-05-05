package admin;

class RegistroAdministrador {

  public static void main(String[] args) {
    RegistroAdministrador nuevoRegistro = new RegistroAdministrador();
    nuevoRegistro.crearAdministrador("lucas", "EstoESUnEjemplo");
  }

  public Administrador crearAdministrador(String usuario, String password) {
    ValidadorPassword unValidador = new ValidadorPassword();
    String passwordVerificada = unValidador.validarPassword(password);
    return new Administrador(usuario,passwordVerificada);
  }
}