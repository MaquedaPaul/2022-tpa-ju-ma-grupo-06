import java.util.List;
import static java.util.Objects.requireNonNull;

public class Sector {
  List<Miembro> miembros;
  public Sector(List<Miembro> unosMiembros){
    //un sector podría estar vacio? si todavía no se asignaron supongo que si
    miembros = unosMiembros;
  }
  void admitirMiembro (Miembro unMiembro){
    miembros.add(unMiembro);
  }
  List<Miembro> getMiembros(){
    return miembros;
  };

}
