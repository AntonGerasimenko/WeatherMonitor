package by.minsk.gerasimenko.anton.weathermonitor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import by.minsk.gerasimenko.anton.weathermonitor.DB.model.ForecastModel;
import by.minsk.gerasimenko.anton.weathermonitor.R;

public class WeatherForecastAdapter extends ArrayAdapter<ForecastModel> {

    private LayoutInflater inflater;

    public WeatherForecastAdapter(Context context, int resource, List<ForecastModel> forecastList) {
        super(context, resource, forecastList);

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.item_weather_city,parent,false);
        }

        ForecastModel forecast = getItem(position);

        TextView tvDate = (TextView) convertView.findViewById(R.id.date);
        TextView tvHight = (TextView) convertView.findViewById(R.id.hight);
        TextView tvLow = (TextView) convertView.findViewById(R.id.low);
        TextView tvText = (TextView) convertView.findViewById(R.id.text);

        tvDate.setText(forecast.getDate());
        tvHight.setText(forecast.getHigh());
        tvLow.setText(forecast.getLow());
        tvText.setText(forecast.getText());

        return convertView;
    }
}
