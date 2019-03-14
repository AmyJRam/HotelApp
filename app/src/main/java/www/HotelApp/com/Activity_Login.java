package www.HotelApp.com;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Activity_Login extends AppCompatActivity implements View.OnClickListener {
    private static String IP = "";
    TextView textView_signUp;
    EditText editText_mailId, editText_password;
    Button button_signIn,button_clear;
    JSONObject jsonObject;
    log_details logDetails;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        init_object();
        register_event();

    }
public void init_object()
{
    context=this;
    logDetails=new log_details(context);
    textView_signUp = (TextView) findViewById(R.id.textView_signup);
    editText_mailId = (EditText) findViewById(R.id.username);
    editText_password = (EditText) findViewById(R.id.password);
    button_signIn = (Button) findViewById(R.id.btnsignup);
    button_clear = (Button) findViewById(R.id.btn_clear);

}
public void register_event()
{
    textView_signUp.setOnClickListener(this);
    button_signIn.setOnClickListener(this);
    button_clear.setOnClickListener(this);
}
    @Override
    public void onClick(View v) {
        if(v==textView_signUp)
        {
            Intent i=new Intent();
            i.setClass(Activity_Login.this,Activity_Register.class);
            startActivity(i);
        }
        if(v==button_signIn)
        {
        String emai_id=editText_mailId.getText().toString();
        String password=editText_password.getText().toString();
            if(validate_user(emai_id,password))
            {
                jsonObject=new JSONObject();
                try
                {

                    jsonObject.put("email_id",emai_id);
                    jsonObject.put("password",password);

                }
                catch (Exception e)
                {

                }
                RequestQueue requestQueue = Volley.newRequestQueue(Activity_Login.this);

                String url = Constant.URL+"UserLog.jsp";

             StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                 @Override
                 public void onResponse(String response) {
                  //   Toast.makeText(context, "Error="+response, Toast.LENGTH_SHORT).show();
                 log_response(response);
                 }
             }, new Response.ErrorListener() {
                 @Override
                 public void onErrorResponse(VolleyError error) {
                  //   Toast.makeText(context, "Error="+error, Toast.LENGTH_SHORT).show();
                 }

             })
             {
                 @Override
                 protected Map<String, String> getParams() throws AuthFailureError {
                     Map<String,String> dataMap = new HashMap<>();

                     dataMap.put("data",jsonObject.toString());

                     return dataMap;
                 }
             };

               requestQueue.add(stringRequest);
            }
        }
        if(v==button_clear)
        {
            editText_mailId.setText("");
            editText_password.setText("");
        }
    }
    public boolean validate_user(String email_id ,String password )
    {
        if(email_id.equals("")){
            editText_mailId.setError("Enter Name");
            return false;
        }

        if(password.equals("")){
            editText_password.setError("Enter Password");
            return false;
        }
        return  true;
    }
    public  void log_response(String response)
    {
        if (response != null) {
            try {

                JSONObject jsonObject = new JSONObject(response);
                int error = jsonObject.getInt("error");
                if (error == 0) {
                    int user_id=jsonObject.getInt("user_id");
                    logDetails.setUser_id(user_id);
                    Intent i=new Intent();
                   // i.setClass(context,QRCode.class);
                    context.startActivity(i);
                    finish();
                }
                else if(error==1)
                {
                    AlertDialog.Builder alBuilder=new AlertDialog.Builder(context);
                    alBuilder.setMessage("Fail to Login");
                    alBuilder.show();
                }
            } catch (Exception e) {
                    System.out.println("Json Error="+e.getMessage());
            }
        }
    }

}