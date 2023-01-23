package server.routers;

import com.sun.xml.internal.ws.api.pipe.Engine;
import controllers.miembro.MiembroController;
import controllers.miembro.SolicitudVinculacionController;
import controllers.miembro.TrayectosController;
import spark.Spark;
import spark.TemplateEngine;

public class RouterMiembro {
  public static void init(TemplateEngine engine) {
    MiembroController miembroController = new MiembroController();
    TrayectosController trayectosController = new TrayectosController();
    SolicitudVinculacionController solicitudVinculacionController = new SolicitudVinculacionController();

    Spark.get("/home/trayectos", trayectosController::getTrayectos, engine);
    Spark.get("/home/trayectos/registro", trayectosController::getRegistrarTrayecto, engine);
    Spark.get("/home/trayectos/registro/tramo-nuevo", trayectosController::getTrayectoNuevo, engine);
    Spark.post("/home/trayectos/registro/tramo-nuevo", trayectosController::cargarTramo, engine);
    Spark.get("/home/trayectos/compartir", miembroController::getRegistro, engine);
    Spark.post("/home/trayectos/compartir", miembroController::getRegistro, engine);
    Spark.post("/home/trayectos/registro/eliminar", trayectosController::eliminarTramo, engine);
    Spark.post("/home/trayectos/registro/cancelar", trayectosController::cancelarTrayecto, engine);
    Spark.post("/home/trayectos/registro/crear", trayectosController::crearTrayecto);
    Spark.get("/home/vinculacion", solicitudVinculacionController::getVinculacion, engine);
    Spark.post("/home/vinculacion", solicitudVinculacionController::pedirVinculacion, engine);
  }
}
