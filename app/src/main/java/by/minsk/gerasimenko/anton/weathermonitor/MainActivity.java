package by.minsk.gerasimenko.anton.weathermonitor;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import by.minsk.gerasimenko.anton.weathermonitor.DB.model.CityModel;
import by.minsk.gerasimenko.anton.weathermonitor.DB.model.ForecastModel;
import by.minsk.gerasimenko.anton.weathermonitor.mvp.fragments.CitysList;
import by.minsk.gerasimenko.anton.weathermonitor.mvp.presenter.MainPresenter;
import by.minsk.gerasimenko.anton.weathermonitor.mvp.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView {

    MainPresenter presenter;
    @Bind(R.id.progressBar)
    ProgressBar pBar;

    Location location ;


    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            MainActivity.this.location = location;
            presenter.locationDetect();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,30 *60*1000l,
                1000.0f, mLocationListener);

        presenter = new MainPresenter(this);

        showProgressBar();
    }

    @Override
    protected void onResume() {
        presenter.start();
        super.onResume();
    }

    @Override
    protected void onStop() {
        presenter.stop();
        super.onStop();
    }


    @Override
    public void showProgressBar() {

        pBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {

        pBar.setVisibility(View.GONE);
    }

    @Override
    public void showCityList(Fragment f, String tag) {

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment, f,tag)
                .commit();
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void updateList(String woeid, List<ForecastModel> model) {
        CitysList fragment = (CitysList) getFragmentManager().findFragmentByTag(CitysList.TAG);
        if (fragment != null) {

            List<CityModel> list = fragment.getList();

            for (CityModel city:list) {

                if (woeid.equals(city.getWoeid())) {

                    city.setForecastModel(model);
                    fragment.updateList();
                    return;
                }
            }
        }
    }

    @Override
    public void showMessage(String text) {
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }




}