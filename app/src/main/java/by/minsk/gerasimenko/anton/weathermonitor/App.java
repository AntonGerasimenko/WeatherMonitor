package by.minsk.gerasimenko.anton.weathermonitor;

import android.app.Application;

import by.minsk.gerasimenko.anton.weathermonitor.DB.DBManager;

/**
 * Created by Anton on 27.02.2016.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.getInstance().init(getApplicationContext());
    }
}
