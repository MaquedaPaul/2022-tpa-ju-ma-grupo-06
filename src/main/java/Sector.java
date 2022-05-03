import java.util.List;
import static java.util.Objects.requireNonNull;

public class Sector {
  List<Miembro> miembros;
  public Sector(Organizacion unaOrganizacion, List<Miembro> unosMiembros){
    requireNonNull(unaOrganizacion);
    //un sector podría estar vacio? si todavía no se asignaron supongo que si
    organizacionAlaQuePertenece = unaOrganizacion;
    miembros = unosMiembros;
  }
  void admitirMiembro (Miembro unMiembro){
    miembros.add(unMiembro);
  }

}
