package registrohc;

import lombok.Getter;
import organizacion.Organizacion;

import javax.persistence.*;

@Getter
@Entity
public class RegistroHCOrganizacion {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  @JoinColumn(name = "ID_ORGANIZACION")
  private Organizacion organizacion;
  private Long hcMediciones;
  private Long hcMiembros;
  private int anioImputacion;
  private int mesImputacion;

  public RegistroHCOrganizacion() {

  }

  public RegistroHCOrganizacion(
      Organizacion org,
      Long hcMediciones,
      Long hcMiembros,
      int anioImputacion,
      int mesInputacion) {

    organizacion = org;
    this.hcMediciones = hcMediciones;
    this.hcMiembros = hcMiembros;
    this.anioImputacion = anioImputacion;
    this.mesImputacion = mesInputacion;
  }


}
