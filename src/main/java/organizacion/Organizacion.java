package organizacion;

import admin.config.GestorDeFechas;
import exceptions.NoExisteElSectorVinculante;
import exceptions.NoSeAceptaVinculacion;
import exceptions.NoSeEncuentraException;
import lombok.Getter;
import mediciones.Medicion;
import mediciones.RepoMediciones;
import notificaciones.Contacto;
import notificaciones.medioNotificacion.MedioNotificador;
import organizacion.periodo.Periodo;
import tipoconsumo.TipoConsumo;
import transporte.Trayecto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
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

  public void procesarVinculacion(boolean decision) {
    Solicitud nuevaSolicitud = solicitudes.get(0);
    solicitudes.remove(0);
    if (decision) {
      aceptarvinculacion(nuevaSolicitud);
      return;
    }
    rechazarVinculacion();
  }

  public void incorporarSector(Sector unSector) {
    sectores.add(unSector);
  }

  public void recibirSolicitud(Solicitud unaSolicitud) {
    validarQueExistaSector(unaSolicitud.nombreDelSectorSolicitado());
    solicitudes.add(unaSolicitud);
  }

  // POR QUE ES UNA EXCEPTION SI NO ES UN ERROR
  private void rechazarVinculacion() {
    throw new NoSeAceptaVinculacion("Su pedido de Vinculacion Fue Rechazado");
  }

  private void validarQueExistaSector(String nombreSector) {
    if (sectores.stream().noneMatch(sector -> sector.getNombre().equals(nombreSector))) {
      throw new NoExisteElSectorVinculante("El organizacion.Sector Ingresado es Invalido");
    }
  }

  private void aceptarvinculacion(Solicitud unaSolicitud) {
    unaSolicitud.getSectorSolicitado().admitirMiembro(unaSolicitud.getMiembroSolicitante());
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

  public int getDiasDeTrabajo() {
    return GestorDeFechas.getInstance().getDiasDeTrabajo();
  }

  /*
   *
   * */

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

//TODO CAMBIAR YEAR Y YEARMONTH POR UNA INTERFACE Y DOS CLASES


  public double indicadorHCMiembros(Periodo periodo) {
    return calcularHCTotal(periodo) / this.getCantidadDeMiembros();
  }

  public double indicadorHCMiembrosEnSector(Sector sector, Periodo periodo) {
    return calcularHCTotal(periodo) / this.getMiembrosEnSector(sector).size();
  }

  public int getCantidadDeMiembros() {
    return this.getMiembros().size();
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

  public Stream<TipoConsumo> getTiposDeConsumoUsados() {
    return this.getMiembros().stream()
        .map(Miembro::getTiposDeConsumoUsados)
        .flatMap(Stream::distinct);
  }
}

