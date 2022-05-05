import exceptions.NoSeAceptaVinculacion;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import exceptions.NoSeAceptaVinculacion;



public class Organizacion {
  String razonSocial;
  TipoOrganizacion tipo;
  String ubicacionGeografica;
  List<Sector> sectores;
  String clasificacion;

  public Organizacion(
      String razonSocial,
      TipoOrganizacion tipo,
      String ubicacionGeografica,
      String clasificacion) {
    this.razonSocial = Objects.requireNonNull(razonSocial);
    this.tipo = Objects.requireNonNull(tipo);
    this.ubicacionGeografica = Objects.requireNonNull(ubicacionGeografica);
    //List<Sector> sectoresVacios = new ArrayList<>();
    this.sectores = new ArrayList<>();
    this.clasificacion = Objects.requireNonNull(clasificacion);
  }

  void procesarPedidoVinculacion(Sector unSector, Miembro unMiembro) {
    if (puedeVincularse(unSector, unMiembro)) {
      unSector.admitirMiembro(unMiembro);
    } else {
      throw new NoSeAceptaVinculacion();
    }
  }

  void crearSector(String nombre, List<Miembro> unosMiembros) {
    sectores.add(new Sector(nombre,unosMiembros));
  }

  // No sabemos bien Como seria la condicion de Vinculacion
  boolean puedeVincularse(Sector unSector, Miembro unMiembro) {
    return sectores.contains(unSector);
  }

  public List<Sector> getSectores() {
    return sectores;
  }
}

