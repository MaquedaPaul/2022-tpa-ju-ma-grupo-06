package organizacion.repositorio;
import lombok.Getter;
import miembro.Miembro;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import organizacion.*;
import transporte.Trayecto;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RepoSolicitud implements WithGlobalEntityManager{
    private static RepoSolicitud repoSolicitud = null;
    static List<Solicitud>  solicitudes = new ArrayList<>();
    private RepoSolicitud() {

    }

    public static void init(){
        Miembro miembro =new Miembro("juan","pedro",TipoDocumento.DNI,32,new ArrayList<>());
        Sector sector = new Sector("nombre", new ArrayList<>());
        Solicitud solicitud1 = new Solicitud(miembro,sector);
        solicitud1.setId(42l);
        Solicitud solicitud2 = new Solicitud(miembro,sector);
        solicitud2.setId(45l);
        repoSolicitud.addSolicitud(solicitud1);
        repoSolicitud.addSolicitud(solicitud2);

    }

    public static RepoSolicitud getInstance() {
        if (repoSolicitud == null) {
            repoSolicitud = new RepoSolicitud();
        }

        return repoSolicitud;
    }
    public void agregarSolicitud(Solicitud nuevaSolicitud) {
        entityManager().getTransaction().begin();
        entityManager().persist(nuevaSolicitud);
        entityManager().getTransaction().commit();
    }
    public static void addSolicitud(Solicitud solicitud){
        solicitudes.add(solicitud);
    }

    public boolean estaPersistido(Solicitud solicitud) {
        return entityManager().contains(solicitud);
    }

    @SuppressWarnings("unchecked")
    public List<Solicitud> getSolicitudes() {
        EntityManager em = getInstance().entityManager();
        return em.createQuery("FROM Solicitud").getResultList();
    }
    public static List<Solicitud> getSolicitudesv2(){
        return solicitudes;
    }

    public void eliminarSolicitud(Solicitud solicitud) {
        entityManager().getTransaction().begin();
        entityManager().remove(solicitud);
        entityManager().getTransaction().commit();
    }
    public Solicitud getSolicitudPor(long id) {
        return getSolicitudes().stream()
                .filter(solicitud -> solicitud.getId().equals(id))
                .findAny().orElse(null);
    }
    public static boolean estaEnLaLista(long id){
        return solicitudes.stream().anyMatch(solicitud -> solicitud.getId().equals(id));
    }
    public static Solicitud obtenerSegunId(long id){
        return solicitudes.stream().filter(solicitud -> solicitud.getId().equals(id)).collect(Collectors.toList()).get(0);
    }


}
