package www.HotelApp.com;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_Register extends AppCompatActivity {

    EditText editText_name,editText_phone,editText_password,editText_shopName,editText_mailId;
    Button button_signUp,button_clear;
    String fcm_token=null;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    Context context;
    View.OnClickListener onClickListener = new ButtonClickListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        context=this;
        progressDialog=new ProgressDialog(this);
        editText_name = (EditText) findViewById(R.id.username);
        editText_password = (EditText) findViewById(R.id.password);
        editText_phone = (EditText) findViewById(R.id.user_password);
        editText_mailId = (EditText) findViewById(R.id.user_mail);
        editText_shopName = (EditText) findViewById(R.id.shop_name);
        button_signUp = (Button) findViewById(R.id.btnsignup  );
        button_clear = (Button) findViewById(R.id.btn_clear  );
        button_signUp.setOnClickListener(onClickListener);
        button_clear.setOnClickListener(onClickListener);
        fcm_token= FirebaseInstanceId.getInstance().getToken();
        requestQueue=Volley.newRequestQueue(context);


    }

    class ButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(v==button_signUp) {
                String username = editText_name.getText().toString();
                String phone = editText_phone.getText().toString();
                String password = editText_password.getText().toString();
                String mail_id = editText_mailId.getText().toString();
                String shop_name = editText_shopName.getText().toString();

                if (isFilled(username, phone, password)) {
                    JSONObject jsonObject = new JSONObject();
                    try {

                        jsonObject.put("name", username);
                        jsonObject.put("phone", phone);
                        jsonObject.put("password", password);
                        jsonObject.put("mail_id", mail_id);
                        jsonObject.put("shop_name", shop_name);
                    } catch (Exception e) {

                    }
                    Map<String, String> dataMap = new HashMap<>();

                    dataMap.put("data", jsonObject.toString());

                    String url = Constant.URL + "SignUp.jsp";

                    StringRequest stringRequest = new RegisterRequest(Request.Method.POST, url,
                            new RegisterResponse(),
                            new RegisterError(), dataMap);
                    requestQueue.add(stringRequest);
                }
            }
            else if(v==button_clear)
            {
                editText_shopName.setText("");
                editText_mailId.setText("");
                editText_name.setText("");
                editText_phone.setText("");
                editText_password.setText("");
            }
        }
    }

    private boolean isFilled(String username, String phone, String password) {
        if(username.equals("")){
            editText_name.setError("Enter Name");
            return false;
        }


        if(phone.equals("")){
            editText_phone.setError("Enter Phone");
            return false;
        }



        if(password.equals(""))
        {
            editText_password.setError("Enter Password");
            return false;
        }

        return true;
    }

    class RegisterRequest extends StringRequest{
        Map<String,String> dataMap;
        public RegisterRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener, Map<String, String> dataMap) {
            super(method, url, listener, errorListener);
            this.dataMap = dataMap;
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return dataMap;
        }
    }


    class RegisterResponse implements Response.Listener<String>{
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                response=jsonObject.getInt("error")+"";

            }
            catch (Exception e)
            {
                Toast.makeText(Activity_Register.this, "response="+response, Toast.LENGTH_SHORT).show();
            }
            response = response.trim();
            if (response.equals("0")){
                Toast.makeText(Activity_Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
            else if(response.equals("1")){
                Toast.makeText(Activity_Register.this, "Unable to Register " +response, Toast.LENGTH_SHORT).show();
            }
        }
    }

    class RegisterError implements Response.ErrorListener{;
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(Activity_Register.this, "Network Error : "+error, Toast.LENGTH_SHORT).show();
        }
    }
}
