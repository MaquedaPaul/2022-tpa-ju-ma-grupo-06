package services.geodds;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import services.geodds.entities.Distancia;

public interface GeoddsService{
  @GET("distancia")
  Call<Distancia> distancia(@Query("localidadOrigenId") int localidadOrigenId,
                            @Query("calleOrigen") String calleOrigen,
                            @Query("alturaOrigen") int altOrigen,
                            @Query("localidadDestinoId") int localidadDestinoId,
                            @Query("calleDestino") String calleDestino,
                            @Query("alturaDestino") int altDestino);
}
