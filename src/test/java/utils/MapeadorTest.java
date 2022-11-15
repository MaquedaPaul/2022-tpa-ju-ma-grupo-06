package utils;

import cuenta.MiembroCuenta;
import miembro.Miembro;
import notificaciones.Contacto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import organizacion.Organizacion;
import organizacion.TipoDocumento;
import organizacion.TipoOrganizacion;
import transporte.Trayecto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapeadorTest {

  String nombre = "Juan";
  String apellido = "Perez";
  TipoDocumento tipoDocumento = TipoDocumento.DNI;
  int nroDoc = 42242424;
  List<Trayecto> trayectos = new ArrayList<>();
  MiembroCuenta cuenta = new MiembroCuenta("juancito", "998Juan");

  @Test
  public void miClaseTiene7Atributos() {

    Miembro juan = new Miembro(nombre, apellido, tipoDocumento, nroDoc, trayectos);
    juan.setCuenta(cuenta);

    // ID + 5 DEL CONSTRUCTOR + CUENTA
    Assertions.assertEquals(Mapeador.mapear(juan).size(), 7);
  }

  @Test
  public void elMapeadorGuardaLosValoresDeMisAtributos() {

    Miembro juan = new Miembro(nombre, apellido, tipoDocumento, nroDoc, trayectos);
    juan.setCuenta(cuenta);

    Map<String, Object> juanMap = Mapeador.mapear(juan);

    Assertions.assertEquals(juanMap.get("nombre"), juan.getNombre());
    Assertions.assertEquals(juanMap.get("apellido"), juan.getApellido());
    Assertions.assertEquals(juanMap.get("trayectos"), juan.getTrayectos());
    Assertions.assertEquals(juanMap.get("numeroDocumento"), juan.getNumeroDocumento());
    Assertions.assertEquals(juanMap.get("cuenta"), juan.getCuenta());
    Assertions.assertEquals(juanMap.get("tipoDocumento"), juan.getTipoDocumento());
    //Si ambos son null -> son iguales
    Assertions.assertEquals(juanMap.get("id"), juan.getId());
  }

  @Test
  public void elMapeadorSeBancaLasListas() {

    List<Contacto> contactos = new ArrayList<>();
    Contacto jorge = new Contacto("Jorge", "jorge123@gmail.com", "155555565");
    Contacto juan = new Contacto("Juan", "juan123@gmail.com", "155655655");
    contactos.add(jorge);
    contactos.add(juan);

    Organizacion org = new Organizacion("a",
        TipoOrganizacion.INSTITUCION,
        "",
        "",
        contactos);


    Map<String, Object> orgMap = Mapeador.mapear(org);

    Assertions.assertEquals(orgMap.get("contactos"), contactos);
    List<Contacto> contactosMapeados = (List<Contacto>) orgMap.get("contactos");
    Assertions.assertEquals(contactos.get(0), contactosMapeados.get(0));
    Assertions.assertEquals(contactos.get(1), contactosMapeados.get(1));

  }
}
