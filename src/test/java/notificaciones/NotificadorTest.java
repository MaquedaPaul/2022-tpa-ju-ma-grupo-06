package notificaciones;

import notificaciones.medioNotificacion.MedioNotificador;
import notificaciones.medioNotificacion.apisMensajeria.AdapterEmail;
import notificaciones.medioNotificacion.apisMensajeria.AdapterWhatsapp;
import org.junit.jupiter.api.Test;
import organizacion.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NotificadorTest {

  Organizacion compraGamer = new Organizacion("Compra Gamer", TipoOrganizacion.EMPRESA, "Calle falsa 123","PG13");
  Contacto juan = new Contacto(compraGamer, "Juan Carlos","juanca@gmail.com","1552207303");
  Contacto jorge = new Contacto(compraGamer, "Jorge Carlos","jorgeca@gmail.com","1552207343");

  @Test
  public void puedoAgregarContactosParaQueSeanNotificados() {
    assertTrue(compraGamer.getContactos().isEmpty());
    compraGamer.cargarContacto(juan);
    compraGamer.cargarContacto(jorge);
    assertTrue(compraGamer.getContactos().contains(jorge) && compraGamer.getContactos().contains(juan));
  }

  @Test
  public void cuandoSeEjecuteElMainSeEnvianLasGuiasALosContactos(){

  }

  @Test
  public void puedoAgregarMediosNuevosAlNotificador() {
    Notificador notificador = new Notificador();
    assertTrue(notificador.medios().isEmpty());

    MedioNotificador medioMail = new AdapterEmail("DEUDA BANCARIA PAGUE O VA PRESO");
    MedioNotificador medioWsp = new AdapterWhatsapp();
    notificador.agregarMedios(medioMail);
    notificador.agregarMedios(medioWsp);

    assertTrue(notificador.medios().contains(medioMail) && notificador.medios().contains(medioWsp));
  }

  @Test
  public void cuandoEnvioNotificacionesPorMailA15ContactosEnvioMail15Veces() {

  }

  @Test
  public void elMensajeDeEnvioPorMailEsPersonalizadoParaCadaContacto() {

  }

  @Test
  public void cuandoEnvioNotificacionesPorWhatsappA15ContactosEnvioMail15Veces() {

  }

  @Test
  public void elMensajeDeEnvioPorWhatsappEsPersonalizadoParaCadaContacto() {

  }
}
