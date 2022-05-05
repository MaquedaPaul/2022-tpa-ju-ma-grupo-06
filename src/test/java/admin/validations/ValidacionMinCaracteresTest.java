package admin.validations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import exceptions.PasswordInseguraException;

public class ValidacionMinCaracteresTest {


    String passwordLarga = "bansdhilasbdlhasbda";
    String passwordJusta = "cocacola";
    String passwordCorta = "123";

    ValidacionMinCaracteres validacion = new ValidacionMinCaracteres();

    /*
     * RegistroAdministrador registro = new RegistroAdministrador(); Administrador administrador =
     * registro.crearAdministrador(user, password);
     * 
     * @Test public void validarPasswordLarga() {
     * 
     * }
     * 
     * @Test public void validarPasswordJusta() {
     * 
     * }
     * 
     */

    @Test
    public void validarPasswordCorta() {
        assertThrows(PasswordInseguraException.class, () -> {
            validacion.validar(passwordCorta);
        });
    }
}
