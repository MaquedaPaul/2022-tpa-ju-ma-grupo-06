package server;

import spark.Spark;

public class Server {
  public static void main(String[] args) {
    Spark.port(9000);
    //Bootstraps.init();
    Router.init();
    Spark.init();
  }
}
