package server.routers;

import controllers.miembro.SolicitudVinculacionController;
import controllers.organizacion.*;
import spark.Spark;
import spark.TemplateEngine;

public class RouterOrganizacion {
  public static void init(TemplateEngine engine) {
    OrganizacionController organizacionController = new OrganizacionController();
    ImpactoController impactoController = new ImpactoController();
    IndicadorController indicadorController = new IndicadorController();
    MedicionController medicionController = new MedicionController();
    VinculacionController vinculacionController = new VinculacionController();
    SolicitudVinculacionController solicitudVinculacionController = new SolicitudVinculacionController();

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

  }
}
