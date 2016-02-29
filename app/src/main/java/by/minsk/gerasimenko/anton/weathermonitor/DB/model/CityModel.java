package by.minsk.gerasimenko.anton.weathermonitor.DB.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.dao.LazyForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import by.minsk.gerasimenko.anton.weathermonitor.rest.body.weatherForecast.Forecast;

@DatabaseTable(tableName="City")
public class CityModel implements Serializable, Comparable<CityModel> {
    @DatabaseField(generatedId = true, dataType = DataType.INTEGER) private int _id;
    @DatabaseField(dataType = DataType.STRING) private String country;
    @DatabaseField(dataType = DataType.STRING) private String woeid;
    @DatabaseField(dataType = DataType.STRING) private String city;
    @DatabaseField(dataType = DataType.FLOAT) private float lang;
    @DatabaseField(dataType = DataType.FLOAT) private float lat;

    private float distance;

   /* @ForeignCollectionField(eager = false)
    private ForeignCollection<ForecastModel> forecastModel;*/


    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    /*public List<ForecastModel> getForecastModel() {

        List<ForecastModel> out = new ArrayList<>();
        for (ForecastModel model:forecastModel) {

            out.add(model);
        }

        return out;
    }

    public void setForecastModel(List<ForecastModel> forecastModel) {
        for (ForecastModel model:forecastModel) {
            this.forecastModel = new LazyForeignCollection<>();
        }

        this.forecastModel = forecastModel;
    }*/

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWoeid() {
        return woeid;
    }

    public void setWoeid(String woeid) {
        this.woeid = woeid;
    }

    public float getLang() {
        return lang;
    }

    public void setLang(float lang) {
        this.lang = lang;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    @Override
    public int compareTo(CityModel another) {
        if (getDistance() > another.getDistance()) return 1;
        if (getDistance() < another.getDistance()) return -1;
        return 0;
    }
}


