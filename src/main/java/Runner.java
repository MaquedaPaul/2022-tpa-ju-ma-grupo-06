import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import organizacion.Organizacion;
import organizacion.RepoOrganizacion;
import organizacion.TipoOrganizacion;
import registrohc.RegistroHCOrganizacion;

import javax.persistence.EntityManager;
import java.time.YearMonth;
import java.util.List;


public class Runner {

  public static void main(String[] args) {

    //evolucionHCTotalOrganizacion

    Organizacion org = new Organizacion("PIRULO SA",
        TipoOrganizacion.EMPRESA,
        "MEDRANO 961",
        "CALSIFICACION",
        null);

    EntityManager em = PerThreadEntityManagers.getEntityManager();

    RepoOrganizacion.getInstance().agregarOrganizacion(org);

    RegistroHCOrganizacion registro1 = new RegistroHCOrganizacion(org, 100L, 100L, 2000, 5);
    RegistroHCOrganizacion registro2 = new RegistroHCOrganizacion(org, 100L, 100L, 2000, 6);
    em.persist(registro1);
    em.persist(registro2);

    List<RegistroHCOrganizacion> resultado = RepoOrganizacion.getInstance().evolucionHCTotal(org, YearMonth.of(2000, 4), YearMonth.of(2000, 6));
    System.out.println(resultado);

  }
}