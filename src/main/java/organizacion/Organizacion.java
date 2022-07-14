package organizacion;

import exceptions.NoExisteElSectorVinculante;
import exceptions.NoSeAceptaVinculacion;
import exceptions.NoSeEncuentraException;
import lombok.Getter;
import notificaciones.Contacto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class Organizacion {
  String razonSocial;
  TipoOrganizacion tipo;
  String ubicacionGeografica;
  List<Sector> sectores;
  String clasificacion;
  List<Solicitud> solicitudes;

  List<Contacto> contactos;


  public Organizacion(String razonSocial, TipoOrganizacion tipo, String ubicacionGeografica,
                      String clasificacion, List<Contacto> contactos) {
    this.razonSocial = Objects.requireNonNull(razonSocial);
    this.tipo = Objects.requireNonNull(tipo);
    this.ubicacionGeografica = Objects.requireNonNull(ubicacionGeografica);
    // List<organizacion.Sector> sectoresVacios = new ArrayList<>();
    this.sectores = new ArrayList<>();
    this.clasificacion = Objects.requireNonNull(clasificacion);
    this.solicitudes = new ArrayList<>();
    this.contactos = contactos;
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

  public double calcularHC() {
    return getSectores().stream().mapToDouble(unSector -> unSector.calcularHCMiembros()).sum();
  }

  public double impactoDeMiembro(Miembro miembro) {
    return (100 * miembro.calcularHCTotal()) / calcularHC();
  }

  public List<Miembro> getMiembros() {
    return getSectores()
        .stream()
        .flatMap(unSector -> (Stream<Miembro>) unSector.getMiembros())
        .collect(Collectors.toList());
  }

  public List<Miembro> getMiembrosEnSector(Sector sector) {
    if (!getSectores().contains(sector)) {
      throw new NoSeEncuentraException(sector + " no pertenece a la organizacion");
    }
    return sector.getMiembros();
  }

  public double indicadorHC_Miembros() {
    return calcularHC() / getMiembros().size();
  }

  public double indicadorHC_MiembrosEnSector(Sector sector) {
    return calcularHC() / this.getMiembrosEnSector(sector).size();
  }

  public void cargarContacto(Contacto unContacto) {
    contactos.add(unContacto);
  }


  public List<Contacto> getContactos() {
    return contactos;
  }

}

