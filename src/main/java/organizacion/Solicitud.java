package organizacion;

import lombok.Getter;
import miembro.Miembro;

import javax.persistence.*;

@Entity(name = "SOLICITUD")
@Getter
public class Solicitud {

  @Id
  @GeneratedValue
  @Column(name = "ID_SOLICITUD")
  Long id;

  @ManyToOne(cascade = CascadeType.PERSIST)
  Miembro miembroSolicitante;

  @ManyToOne(cascade = CascadeType.PERSIST)
  Sector sectorSolicitado;

  private boolean procesada;

  public Solicitud() {
  }

  public Solicitud(Miembro unMiembro, Sector unSector) {
    miembroSolicitante = unMiembro;
    sectorSolicitado = unSector;
  }

  public void aceptarVinculacion() {
    this.sectorSolicitado.admitirMiembro(this.miembroSolicitante);
    procesada = true;
  }

  public void rechazarSolicitud() {
    procesada = true;
  }

  public Miembro getMiembroSolicitante() {
    return miembroSolicitante;
  }
}
