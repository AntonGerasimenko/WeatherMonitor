package by.minsk.gerasimenko.anton.weathermonitor.mvp.presenter;

import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import by.minsk.gerasimenko.anton.weathermonitor.DB.DBService;
import by.minsk.gerasimenko.anton.weathermonitor.DB.model.CityModel;
import by.minsk.gerasimenko.anton.weathermonitor.DB.model.ForecastModel;
import by.minsk.gerasimenko.anton.weathermonitor.mvp.fragments.CitysList;
import by.minsk.gerasimenko.anton.weathermonitor.mvp.view.MainView;
import by.minsk.gerasimenko.anton.weathermonitor.rest.RestClient;
import by.minsk.gerasimenko.anton.weathermonitor.rest.body.weatherForecast.Forecast;
import by.minsk.gerasimenko.anton.weathermonitor.rest.body.weatherForecast.WeatherForecastBody;
import by.minsk.gerasimenko.anton.weathermonitor.rest.body.woeid.WoeidBody;
import retrofit2.Callback;
import retrofit2.Response;



public class MainPresenter  extends AbstractPresenter implements SwipeRefreshLayout.OnRefreshListener {

    MainView mView;

    public MainPresenter(MainView view) {
        this.mView = view;
    }

    @Override
    public void start() {

        List<CityModel> list =  DBService.getAll();
        if (list == null || list.isEmpty()) {


            new LoadStartCities().execute();
        }
    }

    @Override
    public void stop() {

    }

    public void locationDetect() {
        List<CityModel> list =  DBService.getAll();
        if (list != null && !list.isEmpty()) {

            showCityList(list);
        }
    }

    private void showCityList(List<CityModel> list) {
        Location myLocation = mView.getLocation();
        for (CityModel model:list) {

            Location loc = new Location("");
            loc.setLatitude(model.getLat());
            loc.setLongitude(model.getLang());

            model.setDistance(myLocation.distanceTo(loc) / 1000);
        }
        Collections.sort(list);

        mView.showCityList(CitysList.newInstance(list, this), CitysList.TAG);
        mView.hideProgressBar();
    }

    public void update() {
        List<CityModel> list =  DBService.getAll();

        for (CityModel model:list) {

            getForecast(model.getWoeid());
        }
    }

    private void getForecast(final String woeid) {

        RestClient.getInstance().getApiServiceInterface().getWeatherForecast(false, "select * from weather.forecast where woeid=" + woeid, "json").enqueue(new Callback<WeatherForecastBody>() {
            @Override
            public void onResponse(Response<WeatherForecastBody> response) {
                List<Forecast> list = response.body().getQuery().getResults().getChannel().getItem().getForecast();
                List<ForecastModel> out = new ArrayList<>();
                for (Forecast forecast:list) {
                    out.add(new ForecastModel(forecast));
                }

                DBService.putForecast(woeid,out);

                if (!out.isEmpty()) MainPresenter.this.mView.updateList(woeid,out);
            }

            @Override
            public void onFailure(Throwable t) {

                MainPresenter.this.mView.showMessage(t.getMessage());
            }
        });
    }

    private void getWoeid(final CityModel model ) {

        String city = "\""+ model.getCity()+", "+ model.getCountry() + "\"";

        RestClient.getInstance().getApiServiceInterface().getMapPoints(false, "select woeid from geo.places where text=" + city, "json").enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response) {

                JsonObject results = response.body().getAsJsonObject("query").getAsJsonObject("results");
                JsonElement place = results.get("place");

                String woied;

                if (place.isJsonArray()) {

                    woied = ((JsonObject)((JsonArray) place).get(0)).get("woeid").getAsString();
                } else {
                    woied = ((JsonObject) place).get("woeid").getAsString();
                }
                model.setWoeid(woied);

                DBService.putCity(model);

            }

            @Override
            public void onFailure(Throwable t) {
                MainPresenter.this.mView.showMessage(t.getMessage());
            }
        });

    }

    @Override
    public void onRefresh() {

        update();
    }

    private  class LoadStartCities extends AsyncTask<Void,Void,List<CityModel>> {

        private final static String URL_CITIES = "https://shopgate-static.s3.amazonaws.com/worktrail/backend/weather/cities.dat";

        @Override
        protected List<CityModel> doInBackground(Void... params) {
            try {
                URLConnection connection = new URL(URL_CITIES).openConnection();
                InputStream stream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));

                List<CityModel> list = new ArrayList<>();
                boolean firstString = true;
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (firstString) {
                        firstString = false;
                        continue;
                    }

                    if (line.contains("\"") ) {
                        String[] out = new String[5];

                        String[] array = line.trim().split("\"*?\"");
                        String[] temp;
                        out[2] = array[1];
                        temp = array[0].trim().split(" ");
                        out[0] = temp[0];
                        out[1] = temp[1];

                        temp = array[2].trim().split(" ");
                        out[3] = temp[0];
                        out[4] = temp[1];

                       list.add(parse(out));

                    } else   list.add(parse(line.trim().split(" ")));
                }

                return list;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(List<CityModel> list) {

            if (list == null) return;
            for (CityModel model:list) {

                getWoeid(model);
            }


            mView.hideProgressBar();
        }

        private CityModel parse(String[] line) {

            Log.d("City",line.toString());

            CityModel model = new CityModel();
            model.setCountry(line[0]);
            model.setWoeid(line[1]);
            model.setCity(line[2]);
            model.setLang(Float.valueOf(line[3]));
            model.setLat(Float.valueOf(line[4]));

            return model;
        }
    }

}
