package fr.iut_amiens.imagelist.service;

import org.joda.time.Instant;

import java.util.List;

import fr.iut_amiens.imagelist.model.Image;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface IotdService {

    @GET("images")
    @Headers("Accept: application/json")
    Call<List<Image>> downloadImages(@Query("provider") String provider, @Query("before") Instant before, @Query("number") int number);

}
