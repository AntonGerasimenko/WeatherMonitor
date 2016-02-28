package by.minsk.gerasimenko.anton.weathermonitor.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import by.minsk.gerasimenko.anton.weathermonitor.DB.model.CityModel;
import by.minsk.gerasimenko.anton.weathermonitor.DB.model.ForecastModel;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private Dao<CityModel, String> cityDao = null;
    private Dao<ForecastModel, String> forecastDao = null;

    private static final String DATABASE_NAME = "City.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /** * This is called when the database is first created. Usually you should call createTable statements here to create * the tables that will store your data. */
    @Override public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, CityModel.class);
            TableUtils.createTable(connectionSource, ForecastModel.class);
        } catch (SQLException e) {
            throw new RuntimeException(e); }
    }
    /** * This is called when your application is upgraded and it has a higher version number. This allows you to adjust * the various data to match the new version number. */
    @Override public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, CityModel.class, true);
            TableUtils.dropTable(connectionSource, ForecastModel.class, true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override public void close() {
        super.close();
        cityDao = null;
        forecastDao = null;
    }

    public Dao<CityModel, String> getCityDao() throws SQLException {
        if (cityDao == null) {
            cityDao = getDao(CityModel.class);
        }
        return cityDao;
    }

    public Dao<ForecastModel, String> getForecastDao() throws SQLException {
        if (forecastDao == null) {
            forecastDao = getDao(ForecastModel.class);
        }
        return forecastDao;
    }
}
