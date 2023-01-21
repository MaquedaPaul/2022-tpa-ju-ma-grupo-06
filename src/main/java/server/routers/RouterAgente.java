package server.routers;

import controllers.agente.AgenteController;
import controllers.agente.ComposicionController;
import controllers.agente.EvolucionController;
import controllers.agente.OrganizacionesController;
import spark.Spark;
import spark.TemplateEngine;

public class RouterAgente {
  public static void init(TemplateEngine engine) {
    AgenteController agenteController = new AgenteController();
    ComposicionController composicionController = new ComposicionController();
    EvolucionController evolucionController = new EvolucionController();
    OrganizacionesController organizacionesController = new OrganizacionesController();

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
