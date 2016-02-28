package by.minsk.gerasimenko.anton.weathermonitor.rest;


import by.minsk.gerasimenko.anton.weathermonitor.rest.api.ApiServiceInterface;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;


public class RestClient {

    public static volatile RestClient instance;

    public static final String URL =  "http://query.yahooapis.com/v1/";

    private ApiServiceInterface apiServiceInterface;

    public RestClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiServiceInterface = retrofit.create(ApiServiceInterface.class);
    }

    public ApiServiceInterface getApiServiceInterface() {
        return apiServiceInterface;
    }

    public static RestClient getInstance() {
        RestClient localInstance = instance;
        if (localInstance == null) {
            synchronized (RestClient.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new RestClient();
                }
            }
        }
        return localInstance;
    }
}
