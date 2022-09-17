package territorio;

import lombok.Getter;
import organizacion.Organizacion;

import java.util.List;

@Getter
public class SectorTerritorial {
  List<Organizacion> organizaciones;
  TipoSectorTerritorial tipoSectorTerritorial;

  public SectorTerritorial(
      List<Organizacion> organizaciones,
      TipoSectorTerritorial tipoSectorTerritorial) {
    this.organizaciones = organizaciones;
    this.tipoSectorTerritorial = tipoSectorTerritorial;
  }

  public double totalHC(String periodo) {
    return organizaciones.stream().mapToDouble(unaOrg -> unaOrg.calcularHCTotal(periodo)).sum();
  }

  public void incorporarOrganizacion(Organizacion organizacion) {
    organizaciones.add(organizacion);
  }

}
