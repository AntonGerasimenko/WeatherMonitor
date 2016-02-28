package by.minsk.gerasimenko.anton.weathermonitor.mvp.view;

import android.app.Fragment;
import android.location.Location;

import java.util.List;

import by.minsk.gerasimenko.anton.weathermonitor.DB.model.ForecastModel;

/**
 * Created by Anton on 27.02.2016.
 */
public interface MainView  {

    void showProgressBar();
    void hideProgressBar();
    void showCityList(Fragment f, String tag);
    Location  getLocation();

    void updateList(String woeid, List<ForecastModel> model);
    void showMessage(String text);
}
