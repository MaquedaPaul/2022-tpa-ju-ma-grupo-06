package services.geodds;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import services.geodds.entities.Distancia;

import java.io.IOException;

public class ServicioGeodds {
  private static ServicioGeodds instancia = null;
  private static final String urlAPI = "https://ddstpa.com.ar/api/";
  private Retrofit retrofit;

  private ServicioGeodds(){
    this.retrofit = new Retrofit.Builder()
        .baseUrl(urlAPI)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  public static ServicioGeodds getInstance(){
    if(instancia == null){
      instancia = new ServicioGeodds();
    }
    return instancia;
  }

  public Distancia distancia(int locOrig, String calleO, int alturaO, int locDest, String calleD, int alturaD) throws IOException {
    GeoddsService geoddsService = this.retrofit.create(GeoddsService.class);
    Call<Distancia> requestDistancia = geoddsService.distancia(locOrig, calleO, alturaO, locDest, calleD, alturaD);
    Response<Distancia> responseDistancia = requestDistancia.execute();
    return responseDistancia.body();
  }

}
