package organizacion;

import admin.config.GestorDeFechas;
import cuenta.OrganizacionCuenta;
import exceptions.LaFechaDeInicioDebeSerAnteriorALaFechaDeFin;
import exceptions.LaSolicitudNoPerteneceAEstaOrganizacion;
import exceptions.NoExisteElSectorVinculante;
import exceptions.NoSeEncuentraException;
import lombok.Getter;
import mediciones.Medicion;
import mediciones.RepoMediciones;
import miembro.Miembro;
import notificaciones.Contacto;
import notificaciones.medioNotificacion.MedioNotificador;
import organizacion.periodo.Periodo;
import organizacion.periodo.PeriodoMensual;
import transporte.Trayecto;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Entity
public class Organizacion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String razonSocial;

  @Enumerated(EnumType.STRING)
  private TipoOrganizacion tipo;

  //CAMBIAR A PUNTOUBICACION?
  private String ubicacionGeografica;

  private String clasificacion;

  @OneToMany(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "organizacion_id")
  List<Sector> sectores;

  @OneToMany(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "organizacion_id")
  List<Solicitud> solicitudes;

  @OneToMany(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "organizacion_id")
  List<Contacto> contactos;

  @OneToOne(cascade = CascadeType.PERSIST)
  OrganizacionCuenta cuenta;


  public Organizacion(String razonSocial, TipoOrganizacion tipo, String ubicacionGeografica,
                      String clasificacion, List<Contacto> contactos) {
    this.razonSocial = Objects.requireNonNull(razonSocial);
    this.tipo = Objects.requireNonNull(tipo);
    this.ubicacionGeografica = Objects.requireNonNull(ubicacionGeografica);
    this.sectores = new ArrayList<>();
    this.clasificacion = Objects.requireNonNull(clasificacion);
    this.solicitudes = new ArrayList<>();
    this.contactos = contactos;
  }

  public Organizacion() {
  }

  public void procesarVinculacion(Solicitud solicitud, boolean aceptado) {
    if (!this.getSolicitudes().contains(solicitud)) {
      throw new LaSolicitudNoPerteneceAEstaOrganizacion();
    }

    if (aceptado) {
      solicitud.aceptarVinculacion();
    } else {
      solicitud.rechazarSolicitud();
    }
  }

  public void incorporarSector(Sector unSector) {
    sectores.add(unSector);
  }

  public void recibirSolicitud(Solicitud unaSolicitud) {
    validarQueExistaSector(unaSolicitud.getSectorSolicitado().getNombre());
    solicitudes.add(unaSolicitud);
  }

  private void validarQueExistaSector(String nombreSector) {
    if (sectores.stream().noneMatch(sector -> sector.getNombre().equals(nombreSector))) {
      throw new NoExisteElSectorVinculante("El organizacion.Sector Ingresado es Invalido");
    }
  }

  // CALCULAR HC
  public double calcularHCTotal(Periodo periodo) {
    return this.calcularHCTotalMediciones(periodo) + this.calcularHCTotalDeMiembros(periodo);
  }

  public double calcularHCTotalMediciones(Periodo periodo) {
    return this.getMediciones()
        .stream()
        .filter(med -> med.esDelPeriodo(periodo))
        .mapToDouble(med -> med.calcularHC(periodo))
        .sum();
  }

  public double calcularHCTotalDeMiembros(Periodo periodo) {
    return periodo.perioricidad()
        * this.getDiasDeTrabajo()
        * this.getTrayectosDeLosMiembros()
        .mapToDouble(Trayecto::calcularHC)
        .sum();
  }

  public double calcularHCTotalEntre(PeriodoMensual fechaInicio, PeriodoMensual fechaFin) {
    if (fechaInicio.esDespuesDe(fechaFin.getFecha())) {
      throw new LaFechaDeInicioDebeSerAnteriorALaFechaDeFin();
    }
    return this.calcularHCMedicionesEntre(fechaInicio, fechaFin) + this.calcularHCMiembrosEntre(fechaInicio, fechaFin);
  }

  public double calcularHCMiembrosEntre(PeriodoMensual inicio, PeriodoMensual fin) {
    if (inicio.esDespuesDe(fin.getFecha())) {
      throw new LaFechaDeInicioDebeSerAnteriorALaFechaDeFin();
    }
    return this.calcularHCTotalDeMiembros(inicio) * inicio.mesesDeDiferenciaCon(fin);
  }

  public double calcularHCMedicionesEntre(PeriodoMensual inicio, PeriodoMensual fin) {
    return this.getMedicionesEntre(inicio, fin)
        .stream()
        .mapToDouble(medicion -> medicion.calcularHCEntre(inicio, fin))
        .sum();
  }

  public List<Medicion> getMedicionesEntre(PeriodoMensual inicio, PeriodoMensual fin) {
    if (inicio.esDespuesDe(fin.getFecha())) {
      throw new LaFechaDeInicioDebeSerAnteriorALaFechaDeFin();
    }
    return RepoMediciones.getInstance().getMedicionesEntre(inicio, fin);
  }

  public int getDiasDeTrabajo() {
    return GestorDeFechas.getInstance().getDiasDeTrabajo();
  }

  public List<Medicion> getMediciones() {
    return RepoMediciones.getInstance().medicionesDe(this);
  }

  private Stream<Trayecto> getTrayectosDeLosMiembros() {
    return this.getMiembros()
        .stream()
        .map(Miembro::getTrayectos)
        .flatMap(Collection::stream)
        .distinct();
  }

  public double impactoDeMiembro(Miembro miembro, Periodo periodo) {

    return (100 * miembro.calcularHCTotal(periodo)) / this.calcularHCTotal(periodo);
  }

  public List<Miembro> getMiembros() {
    return this.getSectores()
        .stream().map(Sector::getMiembros)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  public List<Miembro> getMiembrosEnSector(Sector sector) {
    if (!getSectores().contains(sector)) {
      throw new NoSeEncuentraException(sector + " no pertenece a la organizacion");
    }
    return sector.getMiembros();
  }

  public double indicadorHCMiembrosEnSector(Sector sector, Periodo periodo) {
    return calcularHCTotal(periodo) / this.getMiembrosEnSector(sector).size();
  }

  public void cargarContacto(Contacto unContacto) {
    contactos.add(unContacto);
  }

  public List<Contacto> getContactos() {
    return contactos;
  }

  public void notificarContactos(List<MedioNotificador> medios) {
    medios.forEach(medioNotificador -> medioNotificador.enviarATodos(contactos, this));
  }

  public Set<String> generarSectoresVacios() {
    return sectores.stream()
        .map(sector -> sector.getNombre()).collect(Collectors.toSet());
  }

  public void setCuenta(OrganizacionCuenta cuenta) {
    this.cuenta = cuenta;
  }
}

