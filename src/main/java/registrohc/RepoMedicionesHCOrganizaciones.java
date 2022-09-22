package registrohc;

import java.util.List;

public class RepoMedicionesHCOrganizaciones {

  private static RepoMedicionesHCOrganizaciones instance;

  private RepoMedicionesHCOrganizaciones() {

  }

  public static RepoMedicionesHCOrganizaciones getInstance() {
    if (instance == null) {
      instance = new RepoMedicionesHCOrganizaciones();
    }
    return instance;
  }

  public void persistirTodas(List<RegistroHCOrganizacion> registros) {
    //TODO persistir todas
  }

  public void persistir(RegistroHCOrganizacion registro) {
    //TODO persistir registros
  }

}
