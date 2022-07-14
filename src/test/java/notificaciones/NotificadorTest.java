package notificaciones;

import notificaciones.medioNotificacion.MedioNotificador;
import notificaciones.medioNotificacion.apisMensajeria.AdapterEmail;
import notificaciones.medioNotificacion.apisMensajeria.AdapterWhatsapp;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.mockito.internal.matchers.Not;
import org.mockito.internal.matchers.Or;
import organizacion.*;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class NotificadorTest {

  Organizacion compraGamer = new Organizacion("Compra Gamer", TipoOrganizacion.EMPRESA, "Calle falsa 123","PG13", new ArrayList<>());
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
    Notificador noti = spy(new Notificador());
    noti.main();
    verify(noti,times(1)).enviarGuias();
  }

  @Test
  public void puedoAgregarMediosNuevosAlNotificador() {
    Notificador notificador = new Notificador();
    assertTrue(notificador.medios().isEmpty());

    MedioNotificador medioMail = new AdapterEmail("DEUDA BANCARIA PAGUE O VA PRESO");
    MedioNotificador medioWsp = new AdapterWhatsapp();
    MedioNotificador medioNotificadorMock = mock(MedioNotificador.class);

    notificador.agregarMedios(medioMail);
    notificador.agregarMedios(medioWsp);
    notificador.agregarMedios(medioNotificadorMock);
    assertTrue(notificador.medios().contains(medioMail));
    assertTrue(notificador.medios().contains(medioWsp));
    assertTrue(notificador.medios().contains(medioNotificadorMock));
  }

  @Test
  public void cuandoEnvioNotificacionesPorMailA15ContactosEnvioMail15Veces() {
    Notificador noti = spy(new Notificador());
    MedioNotificador medio1 = spy(new AdapterEmail("holis"));
    noti.agregarMedios(medio1);
    Contacto contactoMock = mock(Contacto.class);
    ArrayList<Contacto> contactos = new ArrayList<>();
    for(int i = 0; i < 15; i++) {
      contactos.add(contactoMock);
    }
    assertEquals(15, contactos.size());
    doReturn(contactos).when(noti).contactosDeLasOrganizaciones();
    noti.enviarGuias();
    verify(noti,times(1)).contactosDeLasOrganizaciones();
    verify(medio1,times(15)).enviarA(any());
  }

  @Test
  public void cuandoEnvioNotificacionesPorWhatsappA15ContactosEnvioMail15Veces() {
    Notificador noti = spy(new Notificador());
    MedioNotificador medio1 = spy(new AdapterWhatsapp());
    noti.agregarMedios(medio1);
    Contacto contactoMock = mock(Contacto.class);
    ArrayList<Contacto> contactos = new ArrayList<>();
    for(int i = 0; i < 15; i++) {
      contactos.add(contactoMock);
    }
    assertEquals(15, contactos.size());
    doReturn(contactos).when(noti).contactosDeLasOrganizaciones();
    noti.enviarGuias();
    verify(noti,times(1)).contactosDeLasOrganizaciones();
    verify(medio1,times(15)).enviarA(any());
  }

  @Test
  public void aJuacitoLeLlegaUnMensajeDeWhatsappConSuNombre(){
    String mensajeNotificacion = "Hola *NOMBRE_CONTACTO*";
  }

  @Test
  public void aJoseLeLlegaUnMailConSuNombre(){

  }
}
