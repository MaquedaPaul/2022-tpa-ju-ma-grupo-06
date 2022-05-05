import java.util.ArrayList;
import java.util.List;
import java.lang.Exception;
import java.util.Objects;

enum TipoOrganizacion {
  GUBERNAMENTAL,
  ONG,
  EMPRESA,
  INSTITUCION
}

public class Organizacion {
  String razonSocial;
  TipoOrganizacion tipo;
  String ubicacionGeografica;
  List<Sector> sectores;
  String clasificacion;

  public Organizacion(String razonSocial, TipoOrganizacion tipo, String ubicacionGeografica, String clasificacion) {

    this.razonSocial = Objects.requireNonNull(razonSocial);
    this.tipo = Objects.requireNonNull(tipo);
    this.ubicacionGeografica = Objects.requireNonNull(ubicacionGeografica);
    List<Sector> sectoresVacios = new ArrayList<>();
    this.sectores = sectoresVacios;
    this.clasificacion = Objects.requireNonNull(clasificacion);
  }

  void procesarPedidoVinculacion(Sector unSector, Miembro unMiembro) {
    if(puedeVincularse(unSector, unMiembro))
      unSector.admitirMiembro(unMiembro);
    else
      throw new NoSeAceptaVinculacion();
  }
  void crearSector(List<Miembro> unosMiembros) {
    sectores.add(new Sector(unosMiembros));
  }

  // No sabemos bien Como seria la condicion de Vinculacion
  boolean puedeVincularse(Sector unSector, Miembro unMiembro) {
    return true;
  }

  public List<Sector> getSectores() {
    return sectores;
  }
}

class NoSeAceptaVinculacion extends RuntimeException {

}
