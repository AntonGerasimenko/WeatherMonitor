package by.minsk.gerasimenko.anton.weathermonitor.rest.api;


import com.google.gson.JsonObject;

import by.minsk.gerasimenko.anton.weathermonitor.rest.body.weatherForecast.WeatherForecastBody;
import by.minsk.gerasimenko.anton.weathermonitor.rest.body.woeid.WoeidBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiServiceInterface {

    @GET("public/yql")
    Call<JsonObject>  getMapPoints(@Query("diagnostics") boolean diagnostics,
                                  @Query("q") String query,
                                  @Query("format") String format);

    @GET("public/yql")
    Call<WeatherForecastBody> getWeatherForecast(@Query("diagnostics") boolean diagnostics,
                                                 @Query("q") String query,
                                                 @Query("format") String format);
}

