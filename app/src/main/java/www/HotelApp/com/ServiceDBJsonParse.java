package www.HotelApp.com;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amy on 10/24/2017.
 */

public class ServiceDBJsonParse {



    public static List<ServiceDB> parseMovieStringToJson(String movieResultData) {
        List<ServiceDB> ServiceDBList = new ArrayList<>();
        try {
            JSONObject movieResultJsonObject = new JSONObject(movieResultData);

            JSONArray movieResultJsonArray = movieResultJsonObject.getJSONArray("results");
            for (int i = 0; i < movieResultJsonArray.length(); i++) {
                ServiceDB ServiceDB = new ServiceDB();
                ServiceDB.setLocation(movieResultJsonArray.getJSONObject(i).getString("location"));
                ServiceDB.setStatus(movieResultJsonArray.getJSONObject(i).getString("status"));
                ServiceDB.setRequested(movieResultJsonArray.getJSONObject(i).getString("requested"));
                ServiceDB.setShopName(movieResultJsonArray.getJSONObject(i).getString("shopName"));
                ServiceDBList.add(ServiceDB);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ServiceDBList;
    }

}
