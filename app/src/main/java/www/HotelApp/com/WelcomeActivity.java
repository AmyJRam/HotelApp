package www.HotelApp.com;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;



public class WelcomeActivity extends AppCompatActivity {
    public static ArrayList<ServiceDB> arrayList=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this,Activity_Login.class));
                finish();
            }
        },3000);
    }
}
