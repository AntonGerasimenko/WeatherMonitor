package by.minsk.gerasimenko.anton.weathermonitor.mvp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;

import by.minsk.gerasimenko.anton.weathermonitor.DB.model.ForecastModel;
import by.minsk.gerasimenko.anton.weathermonitor.R;
import by.minsk.gerasimenko.anton.weathermonitor.adapters.WeatherForecastAdapter;


public class ForecastDetailDialog extends DialogFragment {

    private String title;
    private List<ForecastModel> forecasts;

    public static ForecastDetailDialog newInstance(String title, List<ForecastModel> forecasts) {

        ForecastDetailDialog instance = new ForecastDetailDialog();
        instance.title = title;
        instance.forecasts = forecasts;

        return instance;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            title = savedInstanceState.getString("title");
            forecasts = (List<ForecastModel>) savedInstanceState.getSerializable("forecasts");
        }

        View view = getActivity().getLayoutInflater().inflate(R.layout.weather_forecast_dialog_view,null);
        ListView weatherView = (ListView) view.findViewById(R.id.forecast_list);

        ListAdapter adapter = new WeatherForecastAdapter(getActivity(),R.layout.item_weather_city,forecasts);
        weatherView.setAdapter(adapter);


        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setNeutralButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setView(view).create();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString("title", title);
        outState.putSerializable("forecasts", (Serializable) forecasts);

        super.onSaveInstanceState(outState);
    }
}
