package services.geodds;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import services.geodds.entities.Distancia;

// EN ESTA INTERFAZ MODELAMOS LA
// PARTE DE LA RUTA RELATIVA DE LA URL, ES DECIR LOS RECURSOS VARIABLES
public interface GeoddsService {

  @GET("distancia")
  Call<Distancia> distancia(
      @Header("Authorization") String token,

      @Query("localidadOrigenId") int localidadOId,

      @Query("calleOrigen") String calleO,

      @Query("alturaOrigen") int altO,

      @Query("localidadDestinoId") int localidadDId,

      @Query("calleDestino") String calleD,

      @Query("alturaDestino") int altD);
}
