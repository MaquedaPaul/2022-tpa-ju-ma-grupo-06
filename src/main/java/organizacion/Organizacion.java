package organizacion;

import exceptions.NoExisteElSectorVinculante;
import exceptions.NoSeAceptaVinculacion;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organizacion {
  String razonSocial;
  TipoOrganizacion tipo;
  String ubicacionGeografica;
  List<Sector> sectores;
  String clasificacion;
  List<Solicitud> solicitudes;

  public Organizacion(String razonSocial, TipoOrganizacion tipo, String ubicacionGeografica,
                      String clasificacion) {
    this.razonSocial = Objects.requireNonNull(razonSocial);
    this.tipo = Objects.requireNonNull(tipo);
    this.ubicacionGeografica = Objects.requireNonNull(ubicacionGeografica);
    // List<organizacion.Sector> sectoresVacios = new ArrayList<>();
    this.sectores = new ArrayList<>();
    this.clasificacion = Objects.requireNonNull(clasificacion);
    this.solicitudes = new ArrayList<>();
  }

  public void procesarVinculacion(boolean decicion) {
    Solicitud nuevaSolicitud = solicitudes.get(0);
    solicitudes.remove(0);
    if (decicion) {
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

  public List<Sector> getSectores() {
    return sectores;
  }

  public String getClasificacion() {
    return clasificacion;
  }

  public String getRazonSocial() {
    return razonSocial;
  }

  public TipoOrganizacion getTipo() {
    return tipo;
  }

  public String getUbicacionGeografica() {
    return ubicacionGeografica;
  }

  public List<Solicitud> getSolicitudes() {
    return solicitudes;
  }
}

