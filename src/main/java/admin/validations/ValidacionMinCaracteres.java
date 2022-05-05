package admin.validations;

import exceptions.PasswordInseguraException;
import java.io.IOException;


public class ValidacionMinCaracteres implements Validacion {

  /**
   * Valida que una password no esté entre las 10 mil peores.
   *
   * @param password
   * @throws IOException
   */
  public void validar(String password) {
    int minCharacters = 8;

    if (password.length() < minCharacters) {
      throw new PasswordInseguraException(
          "La contraseña tiene menos de " + minCharacters + "caracteres.");
    }
  }
}


