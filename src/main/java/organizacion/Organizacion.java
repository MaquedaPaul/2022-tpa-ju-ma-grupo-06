package organizacion;

import admin.config.GestorDeFechas;
import exceptions.NoExisteElSectorVinculante;
import exceptions.NoSeAceptaVinculacion;
import exceptions.NoSeEncuentraException;
import lombok.Getter;
import mediciones.Medicion;
import mediciones.Perioricidad;
import mediciones.RepoMediciones;
import notificaciones.Contacto;
import notificaciones.medioNotificacion.MedioNotificador;
import transporte.Trayecto;

import javax.persistence.*;
import java.time.Year;
import java.time.YearMonth;
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
  Long id;

  String razonSocial;
  TipoOrganizacion tipo;
  String ubicacionGeografica;
  String clasificacion;

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


  public double calcularHCTotal(Year periodo) {
    return this.calcularHCTotalAnualDeMiembros() + this.calcularHcMedicionesAnual(periodo);
  }

  public double calcularHCTotal(YearMonth periodo) {

    return this.calcularHCTotalMensualDeMiembros() + this.calcularHcMedicionesMensual(periodo);
  }

  public double calcularHCTotalAnualDeMiembros() {

    return 12 * this.calcularHCTotalMensualDeMiembros();
  }

  public double calcularHCTotalMensualDeMiembros() {
    return
        GestorDeFechas.getInstance().getDiasDeTrabajo()
            * this.getTrayectosDeLosMiembros()
            .mapToDouble(Trayecto::calcularHC)
            .sum();
  }

  public double calcularHcMedicionesAnual(Year year) {

    return this.getMediciones()
        .stream()
        .filter(medicion -> medicion.esDelAnio(year.getValue()))
        .mapToDouble(medicion -> medicion.getValorSegun(Perioricidad.ANUAL))
        .sum();

  }

  public double calcularHcMedicionesMensual(YearMonth date) {

    return this.getMediciones()
        .stream()
        .filter(medicion -> medicion.esDelAnio(date.getYear()))
        .filter(medicion -> medicion.esAnual() || medicion.esDelMes(date.getMonthValue()))
        .mapToDouble(medicion -> medicion.getValorSegun(Perioricidad.MENSUAL))
        .sum();
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

  public double impactoDeMiembro(Miembro miembro, YearMonth periodo) {

    return (100 * miembro.calcularHCMensual()) / calcularHCTotal(periodo);
  }

  public double impactoDeMiembro(Miembro miembro, Year periodo) {

    return (100 * 12 * miembro.calcularHCMensual()) / calcularHCTotal(periodo);
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

  public double indicadorHC_Miembros(Year periodo) {
    return calcularHCTotal(periodo) / this.getCantidadDeMiembros();
  }

  public double indicadorHC_Miembros(YearMonth periodo) {
    return calcularHCTotal(periodo) / this.getCantidadDeMiembros();
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
}

