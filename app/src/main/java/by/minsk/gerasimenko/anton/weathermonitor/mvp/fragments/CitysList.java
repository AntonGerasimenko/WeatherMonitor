package by.minsk.gerasimenko.anton.weathermonitor.mvp.fragments;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import by.minsk.gerasimenko.anton.weathermonitor.DB.DBService;
import by.minsk.gerasimenko.anton.weathermonitor.DB.model.ForecastModel;
import by.minsk.gerasimenko.anton.weathermonitor.R;
import by.minsk.gerasimenko.anton.weathermonitor.adapters.CityListApadter;
import by.minsk.gerasimenko.anton.weathermonitor.DB.model.CityModel;

public class CitysList extends Fragment implements AdapterView.OnItemClickListener {

    public static final String TAG = "CitysList";

    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    List<CityModel> list = new ArrayList<>();

    private SwipeRefreshLayout.OnRefreshListener swipeListener;

    public static CitysList newInstance(List<CityModel> list, SwipeRefreshLayout.OnRefreshListener swipeListener) {

        CitysList instance = new CitysList();
        instance.list = list;
        instance.swipeListener = swipeListener;

        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.city_list_view, container, false);
        ButterKnife.bind(this, view);


        ListAdapter adapter = new CityListApadter(getActivity(), R.layout.item_city_list, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeListener.onRefresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }


    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    public List<CityModel> getList() {
        return list;
    }

    public void updateList(){

        if (listView!= null && listView.getAdapter()!=null)
        ((ArrayAdapter) listView.getAdapter()).notifyDataSetChanged();
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        CityModel city = list.get(position);
        List<ForecastModel> list = DBService.getForecast(city);

        if (list != null) {

            String title = city.getCity() + " " + String.format("%.1f km", city.getDistance());

            ForecastDetailDialog.newInstance(title, list)
                    .show(getFragmentManager(), "");
        } else Toast.makeText(getActivity(),R.string.no_data, Toast.LENGTH_LONG).show();
    }
}


