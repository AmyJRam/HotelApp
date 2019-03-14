package www.HotelApp.com;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    RecyclerView recyclerView_List;
    Button btn_notification,btn_request;
    private RecyclerView.LayoutManager mLayoutManager;
    Adapter_ServiceList serviceListAdapter;
    List<ServiceDB> serviceDataList;
    String serviceDbUrl;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        context=this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView_List=(RecyclerView)findViewById(R.id.list_history);
        if (recyclerView_List != null) {
            recyclerView_List.setHasFixedSize(true);
        }
        serviceDataList=new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        btn_notification=(Button)findViewById(R.id.button_notification);
        btn_request=(Button)findViewById(R.id.button_request);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Fab Cliked", Toast.LENGTH_SHORT).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        recyclerView_List.setLayoutManager(mLayoutManager);
        btn_request.setOnClickListener(this);
        btn_notification.setOnClickListener(this);
        //enable this function after assigned all values
       // loadServiceData(serviceDbUrl);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent=new Intent();
            intent.setClass(this,Activity_View_Pofile.class);
            context.startActivity(intent);

        } else if (id == R.id.nav_logout) {
            Intent intent=new Intent();
            intent.setClass(this,Activity_Login.class);
            context.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void loadServiceData(String movieDbUrl) {
        URL url = NetworkUtils.buildUrl(movieDbUrl);//here change to your url address;
        new RequestServiceDbData().execute(url);
    }

    public void loadMovieAdapter(String resultData) {
        serviceDataList = ServiceDBJsonParse.parseMovieStringToJson(resultData);
       serviceListAdapter = new Adapter_ServiceList(context, serviceDataList);
        recyclerView_List.setAdapter(serviceListAdapter);
    }

    @Override
    public void onClick(View v) {
        if(v==btn_notification)
        {
            Toast.makeText(context,"textNotification",Toast.LENGTH_LONG).show();
        }
        else if(v==btn_request)
        {
            Toast.makeText(context,"textRequested",Toast.LENGTH_LONG).show();
        }
    }

    class RequestServiceDbData extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String movieResponseData = null;
            URL url = urls[0];
            try {
                movieResponseData = NetworkUtils.getResponseFromMovieDb(url);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("ErrorMessage", e.getMessage());
            }

            return movieResponseData;
        }

        @Override
        protected void onPostExecute(String movieResponseData) {
            super.onPostExecute(movieResponseData);
            Log.d("Data", movieResponseData);
            if (movieResponseData != null) {
                loadMovieAdapter(movieResponseData);
            }
        }
    }


    }

