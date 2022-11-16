package miembro;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import organizacion.Sector;
import organizacion.TipoDocumento;


import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepoMiembros implements WithGlobalEntityManager {
    private static RepoMiembros RepoMiembros = null;
    private RepoMiembros() {

    }


    public static RepoMiembros getInstance() {
        if (RepoMiembros == null) {
            RepoMiembros = new RepoMiembros();
        }

        return RepoMiembros;
    }
    public void agregarMiembro(Miembro miembro) {
        entityManager().getTransaction().begin();
        entityManager().persist(miembro);
        entityManager().getTransaction().commit();
    }


    public boolean estaPersistido(Miembro miembro) {
        return entityManager().contains(miembro);
    }

    @SuppressWarnings("unchecked")
    public List<Miembro> getMiembros() {
        EntityManager em = getInstance().entityManager();
        return em.createQuery("FROM Miembro").getResultList();
    }


    public void eliminarMiembro(Miembro miembros) {
        entityManager().getTransaction().begin();
        entityManager().remove(miembros);
        entityManager().getTransaction().commit();
    }
    public Miembro getMiembrosPor(long id) {
        return getMiembros().stream()
                .filter(miembro -> miembro.getId().equals(id))
                .findAny().orElse(null);
    }


}
