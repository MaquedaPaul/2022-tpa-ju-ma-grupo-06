package services.geodds;

import retrofit2.Call;
import retrofit2.http.*;
import services.geodds.entities.Distancia;

// EN ESTA INTERFAZ MODELAMOS LA PARTE DE LA RUTA RELATIVA DE LA URL, ES DECIR LOS RECURSOS VARIABLES
public interface GeoddsService{

  @GET("distancia")
  Call<Distancia> distancia(@Header("Authorization") String token,
                            @Query("localidadOrigenId") int localidadOrigenId,
                            @Query("calleOrigen") String calleOrigen,
                            @Query("alturaOrigen") int altOrigen,
                            @Query("localidadDestinoId") int localidadDestinoId,
                            @Query("calleDestino") String calleDestino,
                            @Query("alturaDestino") int altDestino);
}
