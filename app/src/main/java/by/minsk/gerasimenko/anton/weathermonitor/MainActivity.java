package by.minsk.gerasimenko.anton.weathermonitor;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
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
import by.minsk.gerasimenko.anton.weathermonitor.DB.DBService;
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

        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

            showGPSDisabledAlertToUser();
        }

        showProgressBar();

        if (!isNetworkConnected()) {

            showMessage(getString(R.string.no_internet));
            hideProgressBar();
        }

        presenter = new MainPresenter(this);
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
    public void updateList() {
        CitysList fragment = (CitysList) getFragmentManager().findFragmentByTag(CitysList.TAG);
        if (fragment != null) {

            List<CityModel> list = DBService.getAll();
            presenter.calcDistance(list);
            Collections.sort(list);

            fragment.setList(list);
            fragment.updateList();
        }
    }

    @Override
    public void showMessage(String text) {
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }

    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
