package repositorios;

import cuenta.AgenteCuenta;
import cuenta.Cuenta;
import miembro.Miembro;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import organizacion.Organizacion;
import territorio.AgenteTerritorial;

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
    return (Cuenta) entityManager()
        .createQuery("FROM Cuenta WHERE usuario = :user").setParameter("user",username)
        .getResultList()
        .get(0);
  }

  //POR QUE ES UNA LISTA????
  public List<Miembro> obtenerMiembro(String usuario) {
    return entityManager()
        .createQuery("from Miembro where Organizacion.cuenta.usuario= :c").setParameter("c", usuario)
        .getResultList();
  }

  public AgenteTerritorial obtenerAgente(Cuenta cuenta) {
    return (AgenteTerritorial) entityManager()
        .createQuery("FROM AgenteTerritorial WHERE cuenta.id = :cuenta_id")
        .setParameter("cuenta_id",cuenta.getId())
        .getResultList()
        .get(0);
  }

  public AgenteCuenta getCuentaAgente(String user) {
    return (AgenteCuenta) entityManager()
        .createQuery("from Cuenta where Cuenta.usuario = :c").setParameter("c", user)
        .getResultList().get(0);
  }

  public List<Organizacion> obtenerOrganizacion(String usuario) {
    return entityManager()
        .createQuery("from Organizacion where Organizacion.cuenta.usuario = :c").setParameter("c", usuario)
        .getResultList();
  }
}
