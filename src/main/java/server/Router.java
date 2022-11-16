package server;

import controllers.*;
import miembro.Miembro;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Router {
  public static void init() {
    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
    RaizController raizController = new RaizController();
    HomeController homeController = new HomeController();
    RecomendacionController recomendacionController = new RecomendacionController();

    SignInController signInController = new SignInController();
    SignOutController signOutController = new SignOutController();

    MiembroController miembroController = new MiembroController();
    OrganizacionController organizacionController = new OrganizacionController();
    AgenteController agenteController = new AgenteController();

    Spark.staticFiles.location("static");

    Spark.after(((request, response) -> {
      PerThreadEntityManagers.getEntityManager().clear();
      PerThreadEntityManagers.closeEntityManager();
    }));
    Spark.get("/", raizController::getPage, engine);
    Spark.get("/recomendaciones", recomendacionController::getRecomendaciones, engine);
    Spark.get("/home",homeController::getHome, engine);
    Spark.get("/signin",signInController::getSignIn, engine);
    Spark.post("/signin", signInController::logIn, engine);
    Spark.post("/signout", signOutController::logOut, engine);
    //GET /miembro/:id/menu;
    //GET /organizaciones/:id/menu
    //GET /agentes/:id/menu
    //Spark.get("/home", (request, response) -> new HomeController().getHome(request, response), engine);

    // Miembro
    //GET /miembro/:id/menu/trayectos
    //get /home/trayectos
    Spark.get("/home/trayectos", miembroController::getTrayectos, engine);
    //GET /miembro/:id/menu/trayectos/registro
    Spark.get("/registro", miembroController::getRegistro, engine);
    Spark.get("/home/registrarTrayecto", miembroController::getRegistrarTrayecto, engine);

    //POST /miembro/:id/menu/trayectos/registro + body
    Spark.get("/home/trayectos/compartir", miembroController::getRegistro, engine);
    Spark.post("/home/trayectos/compartir", miembroController::getRegistro, engine);
    //TODO cual seria el nombre?
    Spark.get("/home/trayectos/eliminar", miembroController::getRegistro, engine);
    Spark.post("/home/trayectos/eliminar", miembroController::getRegistro, engine);

    //GET /miembros/:id/menu/vinculaciones
    //POST /miembros/:id/menu/vinculaciones + body;
    Spark.get("/home/vinculacion", miembroController::getVinculacion, engine);
    Spark.post("/home/vinculacion", miembroController::pedirVinculacion, engine);


    // Organizacion
    //GET /organizaciones/:id/menu/vinculaciones
    Spark.get("/home/vinculaciones", organizacionController::getPage, engine);
    Spark.post("/home/vinculaciones/:id/aceptar", organizacionController::aceptarVinculacion,engine);
    Spark.post("/home/vinculaciones/:id/rechazar", organizacionController::rechazarVinculacion,engine);

    //POST /organizaciones/:id/menu/vinculaciones/:vinculacion + body
    Spark.post("/home/vinculaciones", miembroController::pedirVinculacion, engine);
    //GET /organizaciones/:id/menu/mediciones
    Spark.get("/home/mediciones", organizacionController::getMediciones, engine);
    //GET /organizaciones/:id/menu/mediciones/registro?tipo=medicion
    //POST /organizaciones/:id/menu/mediciones/registro?tipo=medicion + body
    Spark.get("/home/mediciones/perse", organizacionController::getMedicionesPerse, engine);
    Spark.post("/home/mediciones/perse", miembroController::getRegistro, engine);
    //GET /organizaciones/:id/menu/mediciones/registro?tipo=archivo
    //POST /organizaciones/:id/menu/mediciones/registro?tipo=archivo + body;
    Spark.get("/home/mediciones/archivo", organizacionController::getMedicionesArchivo, engine);
    Spark.post("/home/mediciones/archivo", miembroController::getRegistro, engine);
    //GET /organizaciones/:id/menu/calculadora-hc
    Spark.get("/home/calculadora-hc", organizacionController::getCalculadoraHc, engine);
    //GET /organizaciones/:id/menu/calculadora-hc/hc-total
    Spark.get("/home/calculadora-hc/hc-total", organizacionController::getHcTotal, engine);
    //GET /organizaciones/:id/menu/calculadora-hc/impacto-de-miembro?miembro=:id_miembro
    Spark.get("/home/calculadora-hc/impacto-de-miembro/buscador", organizacionController::getImpactoMiembroBuscar, engine);
    Spark.get("/home/calculadora-hc/impacto-de-miembro", organizacionController::getImpactoMiembro, engine);
    Spark.get("/home/calculadora-hc/impacto-de-miembro/:id", organizacionController::getImpactoMiembroConId, engine);
    //GET /organizaciones/:id/menu/calculadora-hc/indicador-hc-sector?sector=:id_sector;
    Spark.get("/home/calculadora-hc/indicador-hc-sector", organizacionController::getIndicadorHcSector, engine);

    //Agente
    //GET /agentes/:id/menu/sectores/:sector/composicion-hc

    //GET /agentes/:id/menu/sectores/:sector/evolucion-hc?desde=:fechaInicio&hasta=:fechaFin
    Spark.get("/home/evolucion-hc", agenteController::getEvolucionHc, engine);
    Spark.get("/home/evolucion-hc/calculo", miembroController::getRegistro, engine);
    //GET /agentes/:id/menu/sectores/:sector/hc-total

    //GET /agentes/:id/menu/sectores/:sector/evolucion-hc?desde=:fechaInicio&hasta=:fechaFin

    //GET /agentes/:id/menu/sectores/:sector/hc-total


    //GET /agentes/:id/menu/sectores/:sector/organizaciones/:organizacion/composicion-hc
    Spark.get("/home/composicion-hc", miembroController::getRegistro, engine);
    Spark.get("/home/composicion-hc", agenteController::getCompocicionHc, engine);

    //GET /agentes/:id/menu/sectores/:sector/organizaciones/hc-total-por-tipo;
    Spark.get("/home/hc-total-por-tipo", miembroController::getRegistro, engine);
    Spark.get("/home/hc-total", agenteController::getHcTotal, engine);
    //GET /agentes/:id/menu/sectores/:sector/organizaciones/:organizacion/evolucion-hc?desde=:fechaInicio&hasta=:fechaFin

    Spark.get("/home/evolucion-hc-organizacion", agenteController::getEvolucionOrganizacion, engine);
    Spark.get("/home/evolucion-hc-organizacion/calculo", miembroController::getRegistro, engine);

  }
}
