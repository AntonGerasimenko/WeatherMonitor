package by.minsk.gerasimenko.anton.weathermonitor.DB.model;


import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import by.minsk.gerasimenko.anton.weathermonitor.rest.body.weatherForecast.Forecast;

@DatabaseTable(tableName="forecast")
public class ForecastModel implements Serializable {

    @DatabaseField(generatedId =  true, dataType = DataType.INTEGER) private int _id;

    @DatabaseField(foreign = true) private CityModel cityModel;

    @DatabaseField(dataType = DataType.STRING) private String date;

    @DatabaseField(dataType = DataType.STRING) private String day;

    @DatabaseField(dataType = DataType.STRING) private String high;

    @DatabaseField(dataType = DataType.STRING) private String low;

    @DatabaseField(dataType = DataType.STRING) private String text;


    public ForecastModel() {
    }

    public ForecastModel(Forecast f) {

        date = f.getDate();
        day = f.getDay();
        high = f.getHigh();
        low = f.getLow();
        text = f.getText();
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CityModel getCityModel() {
        return cityModel;
    }

    public void setCityModel(CityModel cityModel) {
        this.cityModel = cityModel;
    }
}
