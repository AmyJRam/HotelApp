package www.HotelApp.com;

import android.content.Context;
import android.content.SharedPreferences;



public class log_details {
    Context context;
    SharedPreferences sharedPreferences;
    String userkey="user_id";
    String shName="user_details";
    int user_id;
    SharedPreferences.Editor editor;


    public int getUser_id() {
        return sharedPreferences.getInt(userkey,0);
    }

    public void setUser_id(int user_id) {
        //this.user_id = user_id;
        editor.putInt(userkey,user_id);
        editor.commit();
    }


    public log_details(Context context)
    {
        this.context=context;
        sharedPreferences=context.getSharedPreferences(shName,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }
}
