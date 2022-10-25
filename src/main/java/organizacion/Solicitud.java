package organizacion;

import miembro.Miembro;

import javax.persistence.*;

@Entity(name = "SOLICITUD")
public class Solicitud {

  @Id
  @GeneratedValue
  @Column(name = "ID_SOLICITUD")
  Long id;

  @ManyToOne(cascade = CascadeType.PERSIST)
  Miembro miembroSolicitante;
  //@ManyToOne(cascade = CascadeType.PERSIST)
  @ManyToOne(cascade = CascadeType.PERSIST)
  Sector sectorSolicitado;

  public Solicitud(){
  }

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
