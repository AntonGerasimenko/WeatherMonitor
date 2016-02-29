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

    public static List<ForecastModel> getForecast(CityModel model){
        try {
            Dao<ForecastModel,String>     dao = DBManager.getInstance().getHelper().getForecastDao();
            QueryBuilder<ForecastModel, String> builder = dao.queryBuilder();

            builder
                    .orderBy("_id", false)
                    .where()
                    .eq("cityModel_id", model.get_id());

            return dao.query(builder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static void putCity(CityModel model) {
        try {
            Dao<CityModel,String>     dao = DBManager.getInstance().getHelper().getCityDao();
            dao.createOrUpdate(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void putForecast(CityModel model,List<ForecastModel> out) {
        List<ForecastModel> models = getForecast(model);
        delete(models);

        for (ForecastModel forecastModel: out) {

            forecastModel.setCityModel(model);
            putForecast(forecastModel);
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


    public static void putForecast(ForecastModel model) {
        try {
            Dao<ForecastModel,String>     dao = DBManager.getInstance().getHelper().getForecastDao();
            dao.createOrUpdate(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(ForecastModel model) {
        try {
            Dao<ForecastModel,String>     dao = DBManager.getInstance().getHelper().getForecastDao();
            dao.delete(model);
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
        try {
            Dao<ForecastModel,String>     dao = DBManager.getInstance().getHelper().getForecastDao();
            dao.delete(out);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static  void refresh(CityModel  model) {
        /*try {
            //DBManager.getInstance().getHelper().getCityDao().refresh((CityModel) model.getForecastModel());
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }
}
