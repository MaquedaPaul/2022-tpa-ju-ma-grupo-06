package cuenta;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;


public class RepoCuentas implements WithGlobalEntityManager {
  private static RepoCuentas instance;

  private RepoCuentas() {
  }

  public static RepoCuentas getInstance() {
    if (instance == null) {
      instance = new RepoCuentas();
    }
    return instance;
  }

  public void agregarCuenta(Cuenta cuenta) {
    entityManager().getTransaction().begin();
    entityManager().persist(cuenta);
    entityManager().getTransaction().commit();
  }

  public List<Cuenta> getCuentas() {
    return entityManager().createQuery("From Cuenta").getResultList();
  }

  public Cuenta accountByUsername(String username) {
    return getCuentas().stream().filter(cuenta -> cuenta.getUsuario().equals(username)).findAny().orElse(null);
  }

}
