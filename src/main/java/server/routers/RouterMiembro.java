package server.routers;

import controllers.miembro.SolicitudVinculacionController;
import controllers.miembro.TrayectosController;
import spark.Spark;
import spark.TemplateEngine;

public class RouterMiembro {
  public static void init(TemplateEngine engine) {

    TrayectosController trayectosController = new TrayectosController();
    SolicitudVinculacionController solicitudVinculacionController = new SolicitudVinculacionController();

    Spark.get("/home/trayectos", trayectosController::getTrayectos, engine);
    Spark.get("/home/trayectos/nuevo-trayecto", trayectosController::getNuevoTrayecto, engine);
    Spark.get("/home/trayectos/nuevo-trayecto/nuevo-tramo/transporte", trayectosController::getTransporte, engine);
    Spark.get("/home/trayectos/nuevo-trayecto/nuevo-tramo/recorrido", trayectosController::getRecorrido, engine);
    Spark.get("/home/trayectos/nuevo-trayecto/nuevo-tramo/transporte/paradas", trayectosController::getParadas, engine);
    Spark.get("/home/trayectos/nuevo-trayecto/nuevo-tramo/datos-tramo", trayectosController::getDatosTramo, engine);

    Spark.post("/home/trayectos/nuevo-trayecto/nuevo-tramo", trayectosController::postNuevoTramo);
    Spark.post("/home/trayectos/nuevo-trayecto/nuevo-tramo/eliminar", trayectosController::postEliminarDatosBorradorTramo);
    Spark.post("/home/trayectos/nuevo-trayecto/nuevo-tramo/transporte", trayectosController::postTransporte);
    Spark.post("/home/trayectos/nuevo-trayecto/nuevo-tramo/puntos-ubicacion", trayectosController::postPuntosUbicacion);
    Spark.post("/home/trayectos/nuevo-trayecto/borrar-todo", trayectosController::postBorrarTodo);
    Spark.post("/home/trayectos/nuevo-trayecto", trayectosController::postNuevoTrayecto);

    Spark.get("/home/vinculacion", solicitudVinculacionController::getVinculacion, engine);
    Spark.post("/home/vinculacion", solicitudVinculacionController::pedirVinculacion);
  }
}
