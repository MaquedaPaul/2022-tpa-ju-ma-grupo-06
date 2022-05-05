package admin.validations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import exceptions.*;

public class ValidacionPeoresPasswords implements Validacion {

    /**
     * Valida que una password no esté entre las 10 mil peores.
     * 
     * @param password
     * @throws IOException
     */
    public void validar(String password) {
        String path = "../2022-tpa-ju-ma-grupo-06/src/main/java/admin/top_10k_worst_passwords.txt";
        try {
            if (Files.lines(Paths.get(path)).anyMatch(line -> line.contains(password))) {
                throw new PasswordInseguraException("La contraseña está entre las peores 10 mil.");
            }
        } catch (IOException e) {
            throw new FileProblemException("No se pudo comprobar que la contraseña sea segura.");
        }
    }
}
