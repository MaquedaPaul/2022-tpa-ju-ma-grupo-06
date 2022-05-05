import exceptions.NoSeAceptaVinculacion;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;



public class Organizacion {
  String razonSocial;
  TipoOrganizacion tipo;
  String ubicacionGeografica;
  List<Sector> sectores;
  String clasificacion;

  public Organizacion(String razonSocial, TipoOrganizacion tipo, String ubicacionGeografica,
      String clasificacion) {
    this.razonSocial = Objects.requireNonNull(razonSocial);
    this.tipo = Objects.requireNonNull(tipo);
    this.ubicacionGeografica = Objects.requireNonNull(ubicacionGeografica);
    // List<Sector> sectoresVacios = new ArrayList<>();
    this.sectores = new ArrayList<>();
    this.clasificacion = Objects.requireNonNull(clasificacion);
  }

  void procesarPedidoVinculacion(String nombreSector, Miembro unMiembro) {
    if (puedeVincularse(nombreSector, unMiembro)) {
      sectores.stream().filter(sector -> sector.getNombre().equals(nombreSector))
          .collect(Collectors.toList()).get(0).admitirMiembro(unMiembro);
    } else {
      throw new NoSeAceptaVinculacion();
    }
  }

  void crearSector(String nombre, List<Miembro> unosMiembros) {
    sectores.add(new Sector(nombre, unosMiembros));
  }

  // No sabemos bien Como seria la condicion de Vinculacion
  boolean puedeVincularse(String nombreSector, Miembro unMiembro) {
    return sectores.stream().anyMatch(sector -> sector.getNombre().equals(nombreSector));
  }

  public List<Sector> getSectores() {
    return sectores;
  }

  public String getClasificacion() {
    return clasificacion;
  }

  public String getRazonSocial() {
    return razonSocial;
  }

  public TipoOrganizacion getTipo() {
    return tipo;
  }

  public String getUbicacionGeografica() {
    return ubicacionGeografica;
  }
}

