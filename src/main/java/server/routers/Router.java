package server.routers;

import controllers.base.*;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Router {
  public static void init() {
    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
    Spark.staticFiles.location("static");
    RouterGeneral.init(engine);
    RouterMiembro.init(engine);
    RouterOrganizacion.init(engine);
    RouterAgente.init(engine);

    Spark.before(((request, response) -> {
      if (!PerThreadEntityManagers.getEntityManager().getTransaction().isActive()) {
        PerThreadEntityManagers.getEntityManager().getTransaction().begin();
      }
      PerThreadEntityManagers.getEntityManager().flush();
      PerThreadEntityManagers.getEntityManager().close();
    }));

    Spark.before(Validador::validarAcceso);
    Spark.notFound((request, response) -> new ErrorController().get404Page(request, response));
    Spark.after((req, res) -> {
      if (PerThreadEntityManagers.getEntityManager().getTransaction().isActive()) {
        PerThreadEntityManagers.getEntityManager().getTransaction().commit();
      }
    });
  }
}
