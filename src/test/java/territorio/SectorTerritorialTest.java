package territorio;

import admin.FactorEmision;
import global.Unidad;
import linea.LineaTransporte;
import linea.Parada;
import linea.PuntoUbicacion;
import linea.TipoTransporte;
import mediciones.MedicionMensual;
import miembro.Miembro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import organizacion.Organizacion;
import organizacion.Sector;
import organizacion.TipoDocumento;
import organizacion.TipoOrganizacion;
import organizacion.periodo.PeriodoMensual;
import repositorios.*;
import tipoconsumo.TipoActividad;
import tipoconsumo.TipoAlcance;
import tipoconsumo.TipoConsumo;
import transporte.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;



public class SectorTerritorialTest implements WithGlobalEntityManager {

//  SectorTerritorial sector = RepoSectorTerritorial.getInstance().getSectorById(1);
@BeforeEach
public void cargarEnDB(){
  entityManager().getTransaction().begin();
  RepoOrganizacion.getInstance().agregarOrganizacion(unaOrg);
  RepoOrganizacion.getInstance().agregarOrganizacion(otraOrg);
  RepoMediciones.getInstance().cargarMedicion(unaMedicion);
  RepoMediciones.getInstance().cargarMedicion(otraMedicion);
  RepoMediciones.getInstance().cargarMedicion(tercerMedicion);
  RepoTipoDeConsumo.getInstance().agregarNuevoTipoDeConsumo(unTipoConsumo);
  RepoTipoDeConsumo.getInstance().agregarNuevoTipoDeConsumo(otroTipoConsumo);
  RepoMiembros.getInstance().agregarMiembro(unMiembro);
  RepoMiembros.getInstance().agregarMiembro(otroMiembro);
  RepoFactoresEmision.getInstance().incorporarFactor(unFactorEmision);
  RepoFactoresEmision.getInstance().incorporarFactor(otroFactorEmision);
  entityManager().getTransaction().commit();
}


  PuntoUbicacion unPunto = new PuntoUbicacion(342, "Curier", 5);
  PuntoUbicacion otroPunto = new PuntoUbicacion(34223, "Curifer", 5123);
  Organizacion unaOrg = new Organizacion("RazonSocial", TipoOrganizacion.ONG, unPunto,"unaClasificacion", new ArrayList<>());
  TipoConsumo unTipoConsumo = new TipoConsumo("nombre", Unidad.M3, TipoActividad.ELECTRICIDAD_ADQUIRIDA_CONSUMIDA, TipoAlcance.EMISION_DIRECTA);
  FactorEmision unFactorEmision = new FactorEmision(30, Unidad.M3);

  MedicionMensual unaMedicion = new MedicionMensual(unaOrg, unTipoConsumo,LocalDate.now(), 10 );
  List <Parada> unasParadas = new ArrayList<>();
  Parada unaParada = new Parada(0, unPunto);
  {
    unasParadas.add(unaParada);
  }
  List <Parada> otrasParadas = new ArrayList<>();
  Parada otraParada = new Parada(3, otroPunto);
  {
    otrasParadas.add(otraParada);
  }
  LineaTransporte unaLinea = new LineaTransporte(TipoTransporte.COLECTIVO, "A30", unasParadas, otrasParadas);
  TransportePublico vehiculo1 = new TransportePublico(unaLinea,50);
  List<Trayecto> unosTrayectos = new ArrayList<>();
  Set<Tramo> unosTramos = new HashSet<>();
  Tramo unTramo = new Tramo(unPunto, otroPunto, vehiculo1);
  {
    unosTramos.add(unTramo);
  }
  Trayecto unTrayecto = new Trayecto(unosTramos);
  {
    unosTrayectos.add(unTrayecto);
  }
  Miembro unMiembro = new Miembro("Pedro", "Lopez", TipoDocumento.DNI, 3242356, unosTrayectos);



  Organizacion otraOrg = new Organizacion("RazonSocial", TipoOrganizacion.ONG, otroPunto,"unaClasificacion", new ArrayList<>());
  TipoConsumo otroTipoConsumo = new TipoConsumo("nombre", Unidad.M3, TipoActividad.ELECTRICIDAD_ADQUIRIDA_CONSUMIDA, TipoAlcance.EMISION_DIRECTA);
  FactorEmision otroFactorEmision = new FactorEmision(20, Unidad.M3);
  {
    unTipoConsumo.setFactorEmision(unFactorEmision);
    otroTipoConsumo.setFactorEmision(otroFactorEmision);
  }
  MedicionMensual otraMedicion = new MedicionMensual(otraOrg, otroTipoConsumo,LocalDate.now(), 30 );
  LocalDate fecha = LocalDate.now().plusMonths(1);

  TransportePublico vehiculo2 = new TransportePublico(unaLinea,43);
  List<Trayecto> otrosTrayectos = new ArrayList<>();
  Set<Tramo> otrosTramos = new HashSet<>();
  Tramo otroTramo = new Tramo(unPunto, otroPunto, vehiculo2);
  {
    unosTramos.add(otroTramo);
  }
  Trayecto otroTrayecto = new Trayecto(otrosTramos);
  {
    otrosTrayectos.add(otroTrayecto);
  }
  Miembro otroMiembro = new Miembro("Mia", "Lopez", TipoDocumento.DNI, 3242353, otrosTrayectos);
  List<Miembro> miembros = new ArrayList<>();
  List<Miembro> otrosMiembros = new ArrayList<>();
  {
    miembros.add(unMiembro);
    otrosMiembros.add(otroMiembro);
  }
  Sector unSector = new Sector("Ventas", miembros);
  Sector otroSector = new Sector("Marketing", otrosMiembros);

  Combustible unCombustible = new Combustible(unTipoConsumo);
  Combustible otroCombustible = new Combustible(unTipoConsumo);
  {
    vehiculo1.setCombustible(unCombustible);
    vehiculo2.setCombustible(otroCombustible);
  }

  MedicionMensual tercerMedicion = new MedicionMensual(otraOrg, otroTipoConsumo,fecha, 30);


  @Test
  public void unSectorSeCreaYSePuedeIncorporarOrganizacionAlaMisma() {
  SectorTerritorial sectorTerritorial = new SectorTerritorial(new ArrayList<>(), TipoSectorTerritorial.PROVINCIA, "Chubut");
  sectorTerritorial.incorporarOrganizacion(unaOrg);
  List<Organizacion> organizaciones = sectorTerritorial.getOrganizaciones();
  assertEquals(organizaciones.size(),1);
  assertEquals(organizaciones.get(0).getRazonSocial(), unaOrg.getRazonSocial());
  assertEquals(sectorTerritorial.getNombre(), "Chubut");
  assertEquals(sectorTerritorial.getTipoSectorTerritorial(), TipoSectorTerritorial.PROVINCIA);
  }

  @Test
  public void sePuedeCalcularElHCDeLasOrgsTantoDeMiembrosComoDeMediciones(){
    SectorTerritorial sectorTerritorial = new SectorTerritorial(new ArrayList<>(), TipoSectorTerritorial.PROVINCIA, "Chubut");
    sectorTerritorial.incorporarOrganizacion(unaOrg);
    sectorTerritorial.incorporarOrganizacion(otraOrg);
    unaOrg.incorporarSector(unSector);
    otraOrg.incorporarSector(otroSector);
    PeriodoMensual unPeriodo = new PeriodoMensual(LocalDate.now());
    int valorHCMediciones = 40;
    int valorHCMiembros = 167400;
    int valorHCTotal = valorHCMediciones + valorHCMiembros;

    assertEquals(sectorTerritorial.calcularHC(unPeriodo), valorHCTotal);
    assertEquals(sectorTerritorial.calcularHCMediciones(unPeriodo),valorHCMediciones);
    assertEquals(sectorTerritorial.calcularHCMiembros(unPeriodo),valorHCMiembros);

  }

  @Test
  public void sePuedeCalcularElHCDeLasOrgsEntrePeriodos(){
    SectorTerritorial sectorTerritorial = new SectorTerritorial(new ArrayList<>(), TipoSectorTerritorial.PROVINCIA, "Chubut");
    sectorTerritorial.incorporarOrganizacion(unaOrg);
    sectorTerritorial.incorporarOrganizacion(otraOrg);
    unaOrg.incorporarSector(unSector);
    otraOrg.incorporarSector(otroSector);
    PeriodoMensual unPeriodo = new PeriodoMensual(LocalDate.now());
    PeriodoMensual otroPeriodo = new PeriodoMensual(unPeriodo.getFecha().plusMonths(1));
    int valorHCMediciones = 40 + 30;
    int valorHCMiembros = 167435 * 2;
    int valorHCTotal = valorHCMediciones + valorHCMiembros;
    assertEquals(sectorTerritorial.calcularHCEntre(unPeriodo, otroPeriodo), valorHCTotal);

  }








}
