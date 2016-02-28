package by.minsk.gerasimenko.anton.weathermonitor.DB;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import by.minsk.gerasimenko.anton.weathermonitor.DB.model.CityModel;
import by.minsk.gerasimenko.anton.weathermonitor.DB.model.ForecastModel;

public class DBService {

    public static Set<Integer> getDownloadedId(){
        Set<Integer> out = new HashSet<>();

        for (CityModel city:getAll()) {
            out.add(city.get_id());
        }
        return out;
    }

    public static List<CityModel> getAll(){
        try {
            Dao<CityModel,String>     dao = DBManager.getInstance().getHelper().getCityDao();
            List<CityModel> list = dao.queryForAll();

            for(CityModel model:list) {
                refresh(model);
            }
            return  list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static void putCity(CityModel model) {
        try {
            Dao<CityModel,String>     dao = DBManager.getInstance().getHelper().getCityDao();
            dao.create(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void putForecast(String woeid, List<ForecastModel> out) {
        try {
            Dao<CityModel,String>     dao = DBManager.getInstance().getHelper().getCityDao();
            QueryBuilder<CityModel, String> builder = dao.queryBuilder();

            builder
                    .orderBy("_id", false)
                    .where()
                    .eq("woeid", woeid);

            List<CityModel> models = dao.query(builder.prepare());
            for (CityModel model:models) {

                refresh(model);
                delete(model);
                model.setForecastModel(out);

                putCity(model);


            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void putCity(List<CityModel> list) {
        try {
            Dao<CityModel,String>     dao = DBManager.getInstance().getHelper().getCityDao();
            dao.create(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(CityModel model) {
        try {
            Dao<CityModel,String>     dao = DBManager.getInstance().getHelper().getCityDao();
            dao.delete(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(List<ForecastModel> out) {





    }


    private static  void refresh(CityModel  model) {
        /*try {
            //DBManager.getInstance().getHelper().getCityDao().refresh((CityModel) model.getForecastModel());
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }
}
