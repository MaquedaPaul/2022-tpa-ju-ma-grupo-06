package admin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ValidadorPassword {

  public boolean validarPassword(String password) throws IOException {

    if (isInFile(password)) {
      // throw isInFileException("Contraseña vulnerable.");
      return false;
    } else if (password.length() < 8) {
      // throw isTooShort("Contraseña con menos de 8 caracteres.");
      return false;
    }
    return true;
  }

  static boolean isInFile(String password) throws IOException {
    String path = "../2022-tpa-ju-ma-grupo-06/src/main/java/admin/top_10k_worst_passwords.txt";
    return Files.lines(Paths.get(path)).anyMatch(line -> line.contains(password));
  }
}


// public static class isInFileException extends RuntimeException {
// public isInFileException() {
// super
// }
// }


// public static class isTooShortException extends RuntimeException {
// public isInFileException() {
// super
// }
// }
