
package by.minsk.gerasimenko.anton.weathermonitor.rest.body.woeid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WoeidBody {

    @SerializedName("query")
    @Expose
    private Query query;

    /**
     * 
     * @return
     *     The query
     */
    public Query getQuery() {
        return query;
    }

    /**
     * 
     * @param query
     *     The query
     */
    public void setQuery(Query query) {
        this.query = query;
    }

}
