public class Solicitud {
  Miembro miembroSolicitante;
  Sector sectorSolicitado;

  public Solicitud(Miembro unMiembro, Sector unSector) {
    miembroSolicitante = unMiembro;
    sectorSolicitado = unSector;
  }

  public String nombreDelSectorSolicitado() {
    return sectorSolicitado.getNombre();
  }

  public Sector getSectorSolicitado() {
    return sectorSolicitado;
  }

  public Miembro getMiembroSolicitante() {
    return miembroSolicitante;
  }
}
