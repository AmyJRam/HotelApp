package www.HotelApp.com;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Adapter_ServiceList extends RecyclerView.Adapter<Adapter_ServiceList.ViewHolder> {

    Context context;
    ArrayList<ServiceDB> datalist = new ArrayList<>();
    RequestQueue requestQueue;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView location, status,requested,shopName;



        public ViewHolder(View v) {
            super(v);


            location = (TextView) v.findViewById(R.id.location);
            status = (TextView) v.findViewById(R.id.status);
            requested =(TextView) v.findViewById(R.id.requested);
            shopName =(TextView) v.findViewById(R.id.shop_name);

        }
    }

    //Provide a suitable constructor

    public Adapter_ServiceList(Context context, List<ServiceDB> datalist) {
        this.context = context;
        this.datalist = (ArrayList<ServiceDB>) datalist;
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);

    }

    //Create new views (invoked by the layout manager)
    @Override
    public Adapter_ServiceList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Creating a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cart_temp, parent, false);

        //set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //Replace the contents of a view (invoked by the layout manager
    @Override
    public void onBindViewHolder(Adapter_ServiceList.ViewHolder holder, final int position) {


        String datas[] = getItemDetails(datalist.get(position));
        String location = datas[0];
        String status = datas[1];
        String requested = datas[2];
        String shopName = datas[3];
        holder.location.setText(location);
        holder.status.setText(status);
        holder.requested.setText(requested);
        holder.shopName.setText(shopName);



    }

    @Override
    public int getItemCount() {

        return datalist.size();
    }

    public String[] getItemDetails(final ServiceDB itemId) {
        String URL = Constant.URL + "FetchItemData.jsp";
        final String datas[] = new String[2];
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //here mention whichever u given
                    datas[0] = jsonObject.getString("location");
                    datas[1] = jsonObject.getString("status");
                    datas[0] = jsonObject.getString("requested");
                    datas[1] = jsonObject.getString("shopName");
                } catch (Exception e) {
                    System.out.print("JsonError==" + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> dataMap = new HashMap<>();

                dataMap.put("data", String.valueOf(itemId));

                return dataMap;
            }
        };
        requestQueue.add(stringRequest);

        return datas;
    }

    public Bitmap StringToBitmap(String image) {
        Bitmap bitmap;
        try {
            byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            //   Toast.makeText(getApplicationContext(),"IMG"+bitmap,Toast.LENGTH_LONG).show();
            return bitmap;
        } catch (Exception e) {
            Log.d("ImgError", e.getMessage());
            return null;

        }
    }
}