package reporte.itemsreportes;

import organizacion.Organizacion;


public class ComposicionHCOrganizacion {

  private final Organizacion organizacion;
  private final double hcMiembros;
  private final double hcMediciones;


  public ComposicionHCOrganizacion(Organizacion organizacion, double hcMiembros, double hcMediciones) {
    this.organizacion = organizacion;
    this.hcMiembros = hcMiembros;
    this.hcMediciones = hcMediciones;
  }
}
