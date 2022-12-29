package reporte.itemsreportes;

import lombok.Getter;
import organizacion.TipoOrganizacion;

@Getter
public class HCPorTipoOrganizacion {
  double hc;
  TipoOrganizacion unTipo;

  public HCPorTipoOrganizacion(double hc, TipoOrganizacion tipo) {
    this.hc = hc;
    this.unTipo = tipo;
  }

}
