package server;

import cuenta.AgenteCuenta;
import cuenta.MiembroCuenta;
import cuenta.OrganizacionCuenta;
import global.Unidad;
import linea.PuntoUbicacion;
import mediciones.Medicion;
import mediciones.perioricidad.Anual;
import mediciones.perioricidad.Perioricidad;
import miembro.Miembro;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import organizacion.Organizacion;
import organizacion.Sector;
import organizacion.TipoDocumento;
import organizacion.TipoOrganizacion;
import territorio.AgenteTerritorial;
import territorio.SectorTerritorial;
import territorio.TipoSectorTerritorial;
import tipoconsumo.TipoActividad;
import tipoconsumo.TipoAlcance;
import tipoconsumo.TipoConsumo;
import transporte.*;

import java.time.LocalDate;
import java.util.*;

public class BootstrapsMedicion implements WithGlobalEntityManager {
  public static void init() {

    //ORGANIZACIONES

    Organizacion PepsiCo = new Organizacion(
        "PepsiCo S.A",
        TipoOrganizacion.EMPRESA,
        "Vedia 4090",
        "Bebidas",
        new ArrayList<>()
    );

    Organizacion EstudioJuridico = new Organizacion(
        "Estudio Juridico C&D S.R.L",
        TipoOrganizacion.EMPRESA,
        "25 de mayo 4224",
        "Asistencia Legal",
        new ArrayList<>()
    );

    Organizacion Afip = new Organizacion(
        "Afip",
        TipoOrganizacion.GUBERNAMENTAL,
        "San Martin 2621",
        "Agente de Retención",
        new ArrayList<>()
    );

    OrganizacionCuenta cuentaPepsiCo = new OrganizacionCuenta("Pepsi","coca");
    OrganizacionCuenta cuentaEstudioJuridico = new OrganizacionCuenta("Abogados","a");
    OrganizacionCuenta cuentaAfip = new OrganizacionCuenta("Afip","Impuestos");

    PepsiCo.setCuenta(cuentaPepsiCo);
    EstudioJuridico.setCuenta(cuentaEstudioJuridico);
    Afip.setCuenta(cuentaAfip);


    // COSAS DE LOS TRAMOS

    PuntoUbicacion casaMarcos = new PuntoUbicacion(12,"Alzaga",2020);
    PuntoUbicacion estudioJuridico = new PuntoUbicacion(20,"25 de mayo",4224);
    PuntoUbicacion casaJulieta = new PuntoUbicacion(10,"Rivadavia",2930);
    PuntoUbicacion afip = new PuntoUbicacion(11,"San Martin",2621);
    PuntoUbicacion casaGabriel = new PuntoUbicacion(22,"San Lorenzo",2222);
    PuntoUbicacion pepsi = new PuntoUbicacion(22,"Mitre",2222);

    TipoConsumo diesel = new TipoConsumo("diesel", Unidad.LTS, TipoActividad.COMBUSTION_FIJA, TipoAlcance.EMISION_DIRECTA);
    Combustible gasolina = new Combustible(diesel);

    Transporte auto = new VehiculoParticular(TipoVehiculo.AUTO,25,"Fiat 500");
    auto.setCombustible(gasolina);
    Transporte uber = new ServicioContratado(TipoVehiculo.AUTO,13);
    uber.setCombustible(gasolina);
    Transporte moto = new VehiculoParticular(TipoVehiculo.MOTO,19,"Zanella");


    // TRAMOS

    Tramo casaATrabajoMarcos = new Tramo(casaMarcos,estudioJuridico,auto);
    Tramo trabajoACasaMarcos = new Tramo(estudioJuridico,casaMarcos,auto);
    Tramo casaATrabajoJulieta = new Tramo(casaJulieta,afip,uber);
    Tramo trabajoACasaJulieta = new Tramo(afip,casaJulieta,uber);
    Tramo casaATrabajoGabriel = new Tramo(casaGabriel,pepsi,moto);
    Tramo trabajoACasaGabriel = new Tramo(pepsi,casaGabriel,moto);

    //TRAYECTOS

    Set<Tramo> tramosMarcos = new HashSet<>();
    Set<Tramo> tramosJulieta= new HashSet<>();
    Set<Tramo> tramosGabriel = new HashSet<>();
    tramosMarcos.add(casaATrabajoMarcos);
    tramosMarcos.add(trabajoACasaMarcos);
    tramosJulieta.add(casaATrabajoJulieta);
    tramosJulieta.add(trabajoACasaJulieta);
    tramosGabriel.add(casaATrabajoGabriel);
    tramosGabriel.add(trabajoACasaGabriel);

    Trayecto trayecto1Marcos = new Trayecto(tramosMarcos);
    Trayecto trayecto1Julieta = new Trayecto(tramosJulieta);
    Trayecto trayecto1Gabriel = new Trayecto(tramosGabriel);


    List<Trayecto> trayectosMarcos = new ArrayList<>();
    List<Trayecto> trayectosJulieta = new ArrayList<>();
    List<Trayecto> trayectosGabriel = new ArrayList<>();

    trayectosMarcos.add(trayecto1Marcos);
    trayectosJulieta.add(trayecto1Julieta);
    trayectosGabriel.add(trayecto1Gabriel);

    // MIEMBROS

    Miembro Lucas = new Miembro("LUCAS","PRIWHOL", TipoDocumento.DNI,42020300,new ArrayList<>());
    Miembro Gabriel = new Miembro("GABRIEL","ARTUZAR", TipoDocumento.DNI,42010301,trayectosGabriel);
    Miembro Marcos = new Miembro("MARCOS","CALAMARO", TipoDocumento.DNI,42100402,trayectosMarcos);
    Miembro Julieta = new Miembro("JULIETA","PERETTI", TipoDocumento.DNI,45000003,trayectosJulieta);
    Miembro Alejandra = new Miembro("ALEJANDRA","CEBALLOS", TipoDocumento.DNI,46000004,new ArrayList<>());
    Miembro Ricardo = new Miembro("RICARDO","BUSTAMANTE", TipoDocumento.DNI,41000005,new ArrayList<>());

    MiembroCuenta cuentaLucas = new MiembroCuenta("Lucas","1");
    MiembroCuenta cuentaGabriel = new MiembroCuenta("Gabriel","2");
    MiembroCuenta cuentaMarcos = new MiembroCuenta("Marcos","3");
    MiembroCuenta cuentaJulieta = new MiembroCuenta("Julieta","4");
    MiembroCuenta cuentaAlejandra = new MiembroCuenta("Alejandra","5");
    MiembroCuenta cuentaRicardo = new MiembroCuenta("Ricardo","6");

    Lucas.setCuenta(cuentaLucas);
    Gabriel.setCuenta(cuentaGabriel);
    Marcos.setCuenta(cuentaMarcos);
    Julieta.setCuenta(cuentaJulieta);
    Alejandra.setCuenta(cuentaAlejandra);
    Ricardo.setCuenta(cuentaRicardo);

    //SECTORES

    List<Miembro> recepcionistas = new ArrayList<>();
    List<Miembro> vendedores = new ArrayList<>();
    List<Miembro> gerentes = new ArrayList<>();

    Sector recepcion = new Sector("RECEPCION", recepcionistas);
    Sector ventas = new Sector("VENTAS",vendedores);
    Sector gerencia = new Sector("GERENCIA",gerentes);

    recepcion.admitirMiembro(Julieta);
    recepcion.admitirMiembro(Alejandra);

    ventas.admitirMiembro(Gabriel);
    ventas.admitirMiembro(Lucas);

    gerencia.admitirMiembro(Marcos);
    gerencia.admitirMiembro(Ricardo);

    PepsiCo.incorporarSector(ventas);
    EstudioJuridico.incorporarSector(recepcion);
    Afip.incorporarSector(gerencia);

    //SECTOR TERRITORIAL

    List<Organizacion> organizaciones = new ArrayList<>();

    organizaciones.add(PepsiCo);
    organizaciones.add(EstudioJuridico);
    organizaciones.add(Afip);

    SectorTerritorial municipioBuenosAires = new SectorTerritorial(organizaciones, TipoSectorTerritorial.MUNICIPIO);

    //AGENTE SECTORIAL

    AgenteTerritorial gilberto = new AgenteTerritorial(municipioBuenosAires, "gilberto pascualin");

    AgenteCuenta cuentaGilberto = new AgenteCuenta("gilberto","pascualin");

    gilberto.setCuenta(cuentaGilberto);


    //TIPOS DE CONSUMO

    TipoConsumo tipoConsumo1 = new TipoConsumo("Electricidad",Unidad.KWH,TipoActividad.ELECTRICIDAD_ADQUIRIDA_CONSUMIDA,TipoAlcance.EMISION_INDIRECTA_ASOC_ELECTRICIDAD);

    //PERIODICIDAD

    LocalDate medicionAño2021 = LocalDate.of(2021,12,30);
    Perioricidad perioricidad1 = new Anual(medicionAño2021,3333);

    //MEDICIONES

    Medicion medicion1 = new Medicion(tipoConsumo1,perioricidad1,PepsiCo);

    //PERSISTIR

    new Bootstraps().persistir(gilberto); //TODO ¿Guarda todo lo de antes?
    new Bootstraps().persistir(medicion1);

  }

  public void persistir(Object object) {
    entityManager().getTransaction().begin();
    entityManager().persist(object);
    entityManager().getTransaction().commit();
  }

}