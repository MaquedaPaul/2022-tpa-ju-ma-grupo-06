package server;

import controllers.agente.AgenteController;
import controllers.agente.ComposicionController;
import controllers.agente.EvolucionController;
import controllers.agente.OrganizacionesController;
import controllers.base.*;
import controllers.miembro.MiembroController;
import controllers.miembro.SolicitudVinculacionController;
import controllers.miembro.TrayectosController;
import controllers.organizacion.*;
import controllers.base.sign.SignInController;
import controllers.base.sign.SignOutController;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Router {
  public static void init() {
    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
    RaizController raizController = new RaizController();
    ErrorController errorController = new ErrorController();
    HomeController homeController = new HomeController();
    RecomendacionController recomendacionController = new RecomendacionController();

    SignInController signInController = new SignInController();
    SignOutController signOutController = new SignOutController();

    MiembroController miembroController = new MiembroController();
    TrayectosController trayectosController = new TrayectosController();
    SolicitudVinculacionController solicitudVinculacionController = new SolicitudVinculacionController();

    OrganizacionController organizacionController = new OrganizacionController();
    ImpactoController impactoController = new ImpactoController();
    IndicadorController indicadorController = new IndicadorController();
    MedicionController medicionController = new MedicionController();
    VinculacionController vinculacionController = new VinculacionController();

    AgenteController agenteController = new AgenteController();
    ComposicionController composicionController = new ComposicionController();
    EvolucionController evolucionController = new EvolucionController();
    OrganizacionesController organizacionesController = new OrganizacionesController();
    Spark.staticFiles.location("static");


    Spark.before(((request, response) -> {
      if (!PerThreadEntityManagers.getEntityManager().getTransaction().isActive()) {
        PerThreadEntityManagers.getEntityManager().getTransaction().begin();
      }
      PerThreadEntityManagers.getEntityManager().flush();
      PerThreadEntityManagers.getEntityManager().close();
    }));

    Spark.before(Validador::validarAcceso);
    Spark.notFound(errorController::get404Page);
    Spark.after(
        (req, res) ->
        {
          if (PerThreadEntityManagers.getEntityManager().getTransaction().isActive()) {
            PerThreadEntityManagers.getEntityManager().getTransaction().commit();
          }
        }
    );

    //COMUNES PARA TODOS
    Spark.get("/not-found", errorController::get404Page,engine);
    Spark.get("/", raizController::getPage, engine);
    Spark.get("/recomendaciones", recomendacionController::getRecomendaciones, engine);
    Spark.get("/home",homeController::getHome, engine);
    Spark.get("/signin",signInController::getSignIn, engine);
    Spark.post("/signin", signInController::logIn, engine);
    Spark.post("/signout", signOutController::logOut, engine);

    //MIEMBRO
    Spark.get("/home/trayectos", trayectosController::getTrayectos, engine);
    Spark.get("/home/trayectos/registro", trayectosController::getRegistrarTrayecto, engine);
    Spark.get("/home/trayectos/registro/tramo-nuevo", trayectosController::getTrayectoNuevo, engine);
    Spark.post("/home/trayectos/registro/tramo-nuevo", trayectosController::cargarTramo, engine);
    Spark.get("/home/trayectos/compartir", miembroController::getRegistro, engine);
    Spark.post("/home/trayectos/compartir", miembroController::getRegistro, engine);
    Spark.post("/home/trayectos/registro/eliminar", trayectosController::eliminarTramo, engine);
    Spark.post("/home/trayectos/registro/cancelar", trayectosController::cancelarTrayecto, engine);
    Spark.post("/home/trayectos/registro/crear", trayectosController::crearTrayecto, engine);
    Spark.get("/home/vinculacion", solicitudVinculacionController::getVinculacion, engine);
    Spark.post("/home/vinculacion", solicitudVinculacionController::pedirVinculacion, engine);

    //ORGANIZACION
    Spark.get("/home/vinculaciones", vinculacionController::getVinculaciones, engine);
    Spark.post("/home/vinculaciones/:id/aceptar", vinculacionController::aceptarVinculacion,engine);
    Spark.post("/home/vinculaciones/:id/rechazar", vinculacionController::rechazarVinculacion,engine);
    Spark.post("/home/vinculaciones", solicitudVinculacionController::pedirVinculacion, engine);
    Spark.get("/home/mediciones", medicionController::getMediciones, engine);
    Spark.get("/home/mediciones/perse", medicionController::getMedicionesPerse, engine);
    Spark.post("/home/mediciones/perse/creado",medicionController::crearMedicion,engine);
    Spark.get("/home/mediciones/archivo", medicionController::getMedicionesArchivo, engine);
    Spark.post("/home/mediciones/archivo", medicionController::subirCSVs, engine);
    Spark.get("/home/calculadora-hc", organizacionController::getCalculadoraHc, engine);
    Spark.get("/home/calculadora-hc/hc-total", organizacionController::getHcTotal, engine);
    Spark.get("/home/calculadora-hc/impacto-de-miembro/buscador", impactoController::getImpactoMiembroBuscar, engine);
    Spark.get("/home/calculadora-hc/impacto-de-miembro", impactoController::getImpactoMiembro, engine);
    Spark.get("/home/calculadora-hc/impacto-de-miembro/:nombreApellido", impactoController::getImpactoMiembroConNombreYApellido, engine);
    Spark.get("/home/calculadora-hc/indicador-hc-sector/buscador", indicadorController::getIndicadorHcSectorBuscar, engine);
    Spark.get("/home/calculadora-hc/indicador-hc-sector", indicadorController::getIndicadorHcSector, engine);

    //AGENTE
    Spark.get("/home/composicion-hc",composicionController::getComposicionHc,engine);
    Spark.get("/home/composicion-hc/grafico",composicionController::getComposicionHcGrafico,engine);
    Spark.get("/home/evolucion-hc",evolucionController::getEvolucionHc,engine);
    Spark.get("/home/evolucion-hc/grafico",evolucionController::getEvolucionHcGrafico,engine);
    Spark.get("home/organizaciones",organizacionesController::getOrganizaciones,engine);
    Spark.get("home/hc-total",agenteController::getHcTotal,engine);
    Spark.get("/home/organizaciones/hc-tipo-organizacion/:tipo",organizacionesController::getHcTipoOrg,engine);
    Spark.get("/home/organizaciones/:id/evolucion-hc/consulta",evolucionController::getEvolucionHcOrg,engine);
    Spark.get("/home/organizaciones/:id/evolucion-hc/grafico",evolucionController::getEvolucionHcOrgGrafico,engine);
    Spark.get("/home/organizaciones/:id/composicion-hc/consulta",composicionController::getComposicionHcOrg,engine);
    Spark.get("/home/organizaciones/:id/composicion-hc/grafico",composicionController::getComposicionHcOrgGrafico,engine);
  }
}
