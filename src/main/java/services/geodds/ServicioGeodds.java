package services.geodds;

import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import services.geodds.entities.Distancia;


public class ServicioGeodds {
  private static ServicioGeodds instancia = null;
  private static final String urlAPI = "https://ddstpa.com.ar/api/";
  private final Retrofit retrofit;

  private ServicioGeodds() {
    this.retrofit = new Retrofit.Builder()
        .baseUrl(urlAPI)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  public static ServicioGeodds getInstancia() {
    if (instancia == null) {
      instancia = new ServicioGeodds();
    }
    return instancia;
  }
// HACER UN TRY CATCH Y LANZAR UNA EXCEPCION PROPIA
  public Distancia distancia(int locOrig,
                             String calleO,
                             int alturaO,
                             int locDest,
                             String calleD,
                             int alturaD)
      throws IOException {
    GeoddsService geoddsService = this.retrofit.create(GeoddsService.class);
    String token = "Bearer EwiISomWLyg7BW3gnFM0T8Ldj1T7ZMLtaZiaXCnHkJ0=";
    Call<Distancia> requestDistancia = geoddsService.distancia(
        token,
        locOrig,
        calleO,
        alturaO,
        locDest,
        calleD,
        alturaD);
    Response<Distancia> responseDistancia = requestDistancia.execute();
    System.out.println(responseDistancia.code());
    return responseDistancia.body();
  }

}
