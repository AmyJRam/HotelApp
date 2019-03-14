package www.HotelApp.com;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;



public class NetworkUtils {


    /*
   * In this buildUrl() create an instance of Uri  class and also URL class
   * @param movieDbQuery,sortBy
   *@return  URL object
     */

    public static URL buildUrl(String movieDbQuery) {
        Uri movieDbUri = Uri.parse(movieDbQuery).buildUpon()
                .appendQueryParameter(Constant.PARAM_QUERY, Constant.API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(movieDbUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /*
    * In this getResponseFromMovieDb create an instance of HttpURLConnection class
    * @param url
    * and read data from given url
    * and assigned data to movieResponseResult variable
    * @return movieResponseResult
    * finally close the HttpURLConnection object
     */
    public static String getResponseFromMovieDb(URL url) throws IOException {
        String movieResponseResult = null;
        HttpURLConnection movieHttpUrlConnection = (HttpURLConnection) url.openConnection();
        try {

            InputStream movieInputStream = movieHttpUrlConnection.getInputStream();
            Scanner scanner = new Scanner(movieInputStream);
            scanner.useDelimiter("\\A");
            boolean hashInput = scanner.hasNext();
            if (hashInput) {
                movieResponseResult = scanner.next();

            }


        } finally {
            movieHttpUrlConnection.disconnect();
        }

        return movieResponseResult;

    }
}
