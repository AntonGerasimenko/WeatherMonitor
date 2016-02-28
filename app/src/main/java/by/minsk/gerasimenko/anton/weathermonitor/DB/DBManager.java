package by.minsk.gerasimenko.anton.weathermonitor.DB;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;


public class DBManager {
    private static DBManager instance = new DBManager();
    private DatabaseHelper helper;

    public  DatabaseHelper getHelper(){

        return helper;
    }

    public static DBManager getInstance() {
        return instance;
    }

    private DBManager() {
    }

    public void release() {
        if (helper != null) OpenHelperManager.releaseHelper();
    }

    public void init(Context context) {
        if(helper == null) {
            helper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
    }
}
