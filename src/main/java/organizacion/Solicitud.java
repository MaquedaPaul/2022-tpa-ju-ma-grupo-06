package organizacion;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Solicitud {
  @Id
  private Long id;

  @ManyToOne
  @JoinColumn(name = "miembro_id")
  Miembro miembroSolicitante;

  @ManyToOne
  Sector sectorSolicitado;

  public Long getId() {
    return id;
  }

  public Solicitud(Miembro unMiembro, Sector unSector) {
    miembroSolicitante = unMiembro;
    sectorSolicitado = unSector;
  }

  public Solicitud() {
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
