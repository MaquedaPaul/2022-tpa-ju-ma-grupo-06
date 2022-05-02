package admin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ValidadorPassword {

  public static void main(String[] args) throws IOException {

    System.out.println("\nEnter a password:");
    Scanner scanner = new Scanner(System.in);
    String password = scanner.nextLine();

    if (!isInFile(password) && (password.length() >= 8)) {
      System.out.print("\nThe Password is valid!\n\n");
    } else {
      System.out.print("\nThe Password is not valid.\n\n");
    }

  }

  static boolean isInFile(String password) throws IOException {
    String path =
        "/home/fparente/Documents/DDS/2022-tpa-ju-ma-grupo-06/src/main/java/admin/top_10k_worst_passwords.txt";
    return Files.lines(Paths.get(path)).anyMatch(line -> line.contains(password));
  }
}
