package server;

import controllers.*;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Router {
  public static void init() {
    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
    Spark.staticFiles.location("static");
    Spark.get("/", (request, response) -> new RaizController().getPage(request, response), engine);
    Spark.get("/recomendaciones", (request, response) -> new RecomendacionController().getRecomendaciones(request, response), engine);
    Spark.get("/home", (request, response) -> new HomeController().getHome(request, response), engine);
    Spark.get("/signin", (request, response) -> new SignInController().getSignIn(request, response), engine);
    Spark.post("/signin", (request, response) -> new SignInController().logIn(request, response), engine);
    Spark.post("/signout", (request, response) -> new SignOutController().logOut(request, response), engine);
    //GET /miembro/:id/menu;
    //GET /organizaciones/:id/menu
    //GET /agentes/:id/menu
    //Spark.get("/home", (request, response) -> new HomeController().getHome(request, response), engine);

    // Miembro
    //GET /miembro/:id/menu/trayectos
    //get /home/trayectos
    Spark.get("/home/trayectos", (request, response) -> new MiembroController().getTrayectos(request, response), engine);
    //GET /miembro/:id/menu/trayectos/registro
    Spark.get("/registro", (request, response) -> new MiembroController().getRegistro(request, response), engine);
    Spark.get("/home/registrarTrayecto", (request, response) -> new MiembroController().getRegistrarTrayecto(request, response), engine);

    //POST /miembro/:id/menu/trayectos/registro + body
    Spark.get("/home/trayectos/compartir", (request, response) -> new MiembroController().getRegistro(request, response), engine);
    Spark.post("/home/trayectos/compartir", (request, response) -> new MiembroController().getRegistro(request, response), engine);
    //TODO cual seria el nombre?
    Spark.get("/home/trayectos/eliminar", (request, response) -> new MiembroController().getRegistro(request, response), engine);
    Spark.post("/home/trayectos/eliminar", (request, response) -> new MiembroController().getRegistro(request, response), engine);

    //GET /miembros/:id/menu/vinculaciones
    //POST /miembros/:id/menu/vinculaciones + body;
    Spark.get("/vinculacion", (request, response) -> new MiembroController().getVinculacion(request, response), engine);
    Spark.post("/home/vinculacion", (request, response) -> new MiembroController().getRegistro(request, response), engine);

    // Organizacion
    //GET /organizaciones/:id/menu/vinculaciones
    Spark.get("/home/vinculaciones", (request, response) -> new MiembroController().getRegistro(request, response), engine);
    //POST /organizaciones/:id/menu/vinculaciones/:vinculacion + body
    Spark.post("/home/vinculaciones", (request, response) -> new MiembroController().getRegistro(request, response), engine);
    //GET /organizaciones/:id/menu/mediciones
    Spark.get("/home/mediciones", (request, response) -> new MiembroController().getRegistro(request, response), engine);
    //GET /organizaciones/:id/menu/mediciones/registro?tipo=medicion
    //POST /organizaciones/:id/menu/mediciones/registro?tipo=medicion + body
    Spark.get("/home/mediciones/perse", (request, response) -> new MiembroController().getRegistro(request, response), engine);
    Spark.post("/home/mediciones/perse", (request, response) -> new MiembroController().getRegistro(request, response), engine);
    //GET /organizaciones/:id/menu/mediciones/registro?tipo=archivo
    //POST /organizaciones/:id/menu/mediciones/registro?tipo=archivo + body;
    Spark.get("/home/mediciones/archivo", (request, response) -> new MiembroController().getRegistro(request, response), engine);
    Spark.post("/home/mediciones/archivo", (request, response) -> new MiembroController().getRegistro(request, response), engine);
    //GET /organizaciones/:id/menu/calculadora-hc
    Spark.get("/home/calculadorahc", (request, response) -> new MiembroController().getRegistro(request, response), engine);
    //GET /organizaciones/:id/menu/calculadora-hc/hc-total
    Spark.get("/home/calculadorahc/hc-total", (request, response) -> new MiembroController().getRegistro(request, response), engine);
    //GET /organizaciones/:id/menu/calculadora-hc/impacto-de-miembro?miembro=:id_miembro
    Spark.get("/home/calculadorahc/impacto-de-miembro", (request, response) -> new MiembroController().getRegistro(request, response), engine);
    //GET /organizaciones/:id/menu/calculadora-hc/indicador-hc-sector?sector=:id_sector;
    Spark.get("/home/calculadorahc/indicador-hc-sector", (request, response) -> new MiembroController().getRegistro(request, response), engine);

    //Agente
    //GET /agentes/:id/menu/sectores/:sector/composicion-hc
    Spark.get("/home/composicion-hc", (request, response) -> new MiembroController().getRegistro(request, response), engine);
    //GET /agentes/:id/menu/sectores/:sector/evolucion-hc?desde=:fechaInicio&hasta=:fechaFin
    Spark.get("/home/evolucion-hc", (request, response) -> new MiembroController().getRegistro(request, response), engine);
    Spark.get("/home/evolucion-hc/calculo", (request, response) -> new MiembroController().getRegistro(request, response), engine);
    //GET /agentes/:id/menu/sectores/:sector/hc-total
    Spark.get("/home/hc-total", (request, response) -> new MiembroController().getRegistro(request, response), engine);
    //GET /agentes/:id/menu/sectores/:sector/organizaciones/:organizacion/composicion-hc
    Spark.get("/home/composicion-hc", (request, response) -> new MiembroController().getRegistro(request, response), engine);
    //GET /agentes/:id/menu/sectores/:sector/organizaciones/hc-total-por-tipo;
    Spark.get("/home/hc-total-por-tipo", (request, response) -> new MiembroController().getRegistro(request, response), engine);
    //GET /agentes/:id/menu/sectores/:sector/organizaciones/:organizacion/evolucion-hc?desde=:fechaInicio&hasta=:fechaFin
    Spark.get("/home/evolucion-hc-organizacion", (request, response) -> new MiembroController().getRegistro(request, response), engine);
    Spark.get("/home/evolucion-hc-organizacion/calculo", (request, response) -> new MiembroController().getRegistro(request, response), engine);
  }
}
