package repositorios;

import com.sun.org.apache.xpath.internal.operations.Or;
import miembro.Miembro;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import organizacion.*;

import java.util.List;

import java.util.Set;
import java.util.stream.Collectors;

public class RepoSolicitud implements WithGlobalEntityManager{
    private static RepoSolicitud repoSolicitud;

    private RepoSolicitud() {

    }

    public static RepoSolicitud getInstance() {
        if (repoSolicitud == null) {
            repoSolicitud = new RepoSolicitud();
        }

        return repoSolicitud;
    }

    public Solicitud getSolicitudById(long id) {
        return entityManager().find(Solicitud.class,id);
    }

    public void addSolicitud(Solicitud solicitud) {
        entityManager().persist(solicitud);
    }

    private List<Solicitud> getSolicitudes() {
        return entityManager().createQuery("FROM Solicitud").getResultList();
    }

    public Set<Solicitud> getSolicitudesDe(Organizacion organizacion) {
        return RepoSolicitud.getInstance()
            .getSolicitudes()
            .stream()
            .filter(s -> s.perteneceA(organizacion))
            .collect(Collectors.toSet());
    }

    public boolean miembroTieneSolicitudConOrg(Miembro miembro, Organizacion organizacionObjetivo) {
        Solicitud solicitudCumpleParametros = this.getSolicitudes().stream()
                .filter(solicitud -> this.miembroYOrgInvolucradosEn(solicitud, miembro, organizacionObjetivo))
                .findAny().orElse(null);
        return solicitudCumpleParametros != null && noEsProcesadaYPerteneceALaOrg(solicitudCumpleParametros, organizacionObjetivo);
    }
    private boolean noEsProcesadaYPerteneceALaOrg(Solicitud solicitud, Organizacion organizacion){
        return !solicitud.isProcesada() && solicitud.perteneceA(organizacion);
    }
    private boolean miembroYOrgInvolucradosEn(Solicitud solicitud, Miembro miembro, Organizacion organizacion){
        return solicitud.getMiembroSolicitante().equals(miembro) && solicitud.perteneceA(organizacion);
    }

}
