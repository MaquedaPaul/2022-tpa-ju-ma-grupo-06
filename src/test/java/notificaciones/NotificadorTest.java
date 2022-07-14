package notificaciones;

import notificaciones.medioNotificacion.MedioNotificador;
import notificaciones.medioNotificacion.apisMensajeria.AdapterEmail;
import notificaciones.medioNotificacion.apisMensajeria.AdapterWhatsapp;
import org.junit.jupiter.api.Test;
import organizacion.*;

import java.time.LocalDate;
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

    MedioNotificador medioMail = new AdapterEmail();
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
    MedioNotificador medio1 = spy(new AdapterEmail());
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
  public void aJuancitoLeLlegaUnMensajeDeWhatsappConSuNombre(){
    String mensajeNotificacion = "Hola *NOMBRE_CONTACTO* de *ORGANIZACION*, te envio el link a la pagina del mes *MES*: *URL*";
    String url = "www.pagina.com/index.html";
    String asunto = "Recomendaciones *MES/AÑO*";
    String mesActual = LocalDate.now().getMonth().toString();
    int anioActual = LocalDate.now().getYear();
    MedioNotificador medioMock = spy(new AdapterWhatsapp());
    medioMock.setMensaje(mensajeNotificacion);
    medioMock.setUrl(url);
    medioMock.setAsunto(asunto);
    Organizacion onu = new Organizacion("ONU", TipoOrganizacion.INSTITUCION, "texto2", "texto3", new ArrayList<>());
    Contacto jorge = new Contacto(onu,"Jorge Nitales","jorgeni@gmail.com","1552207070");
    String asuntoEsperado = "RECOMENDACIONES " + mesActual + " " + anioActual;
    String mensajeEsperado = "Hola Jorge Nitales de ONU, te envio el link a la pagina del mes " + mesActual + ": " + url;
    String mensajeTotal = asuntoEsperado + "\n" + mensajeEsperado;
    assertEquals(mensajeTotal,medioMock.mensajePersonalizadoPara(jorge));
  }
  @Test
  public void laPlantillaDelMailBienSeteadaDeberiaReemplazarCorrectamenteLosCampos(){
    //Contacto juan = new Contacto(compraGamer, ,"juanca@gmail.com","1552207303");
    //*ORGANIZACION* = "Compra Gamer"
    //*NOMBRE_CONTACTO* "Juan Carlos"
    AdapterEmail unAdaptarEmail = new AdapterEmail();
    String cuerpo = "Hola *NOMBRE_CONTACTO* de la organización *ORGANIZACION* te enviamos" +
        " esta notificacion el mes *MES* con la url *URL*";

    unAdaptarEmail.setMensaje(cuerpo);
    unAdaptarEmail.setUrl("prueba123.com");
    unAdaptarEmail.setAsunto("Asunto urgente *MES/AÑO*");
    assertEquals(unAdaptarEmail.getMensaje(), cuerpo);
    assertEquals(unAdaptarEmail.mensajePersonalizadoPara(juan),
        "Hola Juan Carlos de la organización Compra Gamer te enviamos" +
            " esta notificacion el mes JULY con la url prueba123.com" );
    assertEquals(unAdaptarEmail.getAsunto(), "ASUNTO URGENTE JULY 2022");


  }

  @Test
  public void laPlantillaDeWhatsappBienSeteadaDeberiaReemplazarCorrectamenteLosCampos() {

    //Contacto juan = new Contacto(compraGamer, ,"juanca@gmail.com","1552207303");
    //*ORGANIZACION* = "Compra Gamer"
    //*NOMBRE_CONTACTO* "Juan Carlos"
    AdapterWhatsapp unAdaptarWhatsapp = new AdapterWhatsapp();
    String cuerpo =
        "Hola *NOMBRE_CONTACTO* de la organización *ORGANIZACION* te enviamos" +
        " esta notificacion el mes *MES* con la url *URL*";

    unAdaptarWhatsapp.setMensaje(cuerpo);
    unAdaptarWhatsapp.setUrl("prueba123.com");
    unAdaptarWhatsapp.setAsunto("Asunto urgente *MES/AÑO*");
    assertEquals(unAdaptarWhatsapp.getMensaje(), cuerpo);
    assertEquals(unAdaptarWhatsapp.mensajePersonalizadoPara(juan),
        "ASUNTO URGENTE JULY 2022\n" +
            "Hola Juan Carlos de la organización Compra Gamer te enviamos" +
            " esta notificacion el mes JULY con la url prueba123.com");
    assertEquals(unAdaptarWhatsapp.getAsunto(), "ASUNTO URGENTE JULY 2022");

  }

  @Test
  public void aJoseLeLlegaUnMailConSuNombre(){
    String mensajeNotificacion = "Hola *NOMBRE_CONTACTO* de *ORGANIZACION*, te envio el link a la pagina del mes *MES*: *URL*";
    String url = "www.pagina.com/index.html";
    String mesActual = LocalDate.now().getMonth().toString();
    MedioNotificador medioMock = spy(new AdapterEmail());
    medioMock.setMensaje(mensajeNotificacion);
    medioMock.setUrl(url);
    Organizacion onu = new Organizacion("ONU", TipoOrganizacion.INSTITUCION, "texto2", "texto3", new ArrayList<>());
    Contacto jose = new Contacto(onu,"Jose Pereira","jorgeni@gmail.com","1552207070");
    String mensajeEsperado = "Hola Jose Pereira de ONU, te envio el link a la pagina del mes " + mesActual + ": " + url;
    assertEquals(mensajeEsperado,medioMock.mensajePersonalizadoPara(jose));
  }

  @Test
  public void elAsuntoDeUnMailCambiaSegunLaFecha() {
    //existe algo para mockear fechas?
    MedioNotificador mailer = new AdapterEmail();
    mailer.setAsunto("Asunto urgente *MES/AÑO*");
    String mesActual = LocalDate.now().getMonth().toString();
    int anioActual = LocalDate.now().getYear();
    assertEquals(mailer.getAsunto(),"ASUNTO URGENTE " + mesActual + " " + anioActual);
  }
}
