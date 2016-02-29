package by.minsk.gerasimenko.anton.weathermonitor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;


import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import by.minsk.gerasimenko.anton.weathermonitor.DB.DBService;
import by.minsk.gerasimenko.anton.weathermonitor.DB.model.ForecastModel;
import by.minsk.gerasimenko.anton.weathermonitor.R;
import by.minsk.gerasimenko.anton.weathermonitor.DB.model.CityModel;

/**
 * Created by Anton on 26.02.2016.
 */
public class CityListApadter extends ArrayAdapter<CityModel>{

    private LayoutInflater inflater;

    public CityListApadter(Context context, int resource, List<CityModel> citys) {
        super(context, resource, citys);

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null || !(convertView.getTag() instanceof Holder)) {

            convertView = inflater.inflate(R.layout.item_city_list,parent,false);
            holder = new Holder(convertView);
            convertView.setTag(convertView);
        } else {
            holder = (Holder) convertView.getTag();
        }

        CityModel city =  getItem(position);

        holder.tvCity.setText(city.getCity());
        holder.tvDistance.setText(String.format("%.1f km", city.getDistance()));

        List<ForecastModel> list = DBService.getForecast(city);


        if (list != null && !list.isEmpty()) {

            ForecastModel model = list.get(0);

            holder.tvDate.setText(model.getDate());
            holder.tvHigh.setText(model.getHigh());
            holder.tvLow.setText(model.getLow());
            holder.tvText.setText(model.getText());
            holder.llWeatherContainer.setVisibility(View.VISIBLE);
        } else {
            holder.llWeatherContainer.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    static class Holder {
        @Bind(R.id.city)
        TextView tvCity;
        @Bind(R.id.distance)
        TextView tvDistance;
        @Bind(R.id.date)
        TextView tvDate;
        @Bind(R.id.hight)
        TextView tvHigh;
        @Bind(R.id.low)
        TextView tvLow;
        @Bind(R.id.text)
        TextView tvText;
        @Bind(R.id.weather_container)
        LinearLayout llWeatherContainer;

        public Holder(View v) {
            ButterKnife.bind(this,v);
        }
    }
}
