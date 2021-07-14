package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    String CityName="";
    ImageView imgback;
    TextView txtName;
    ListView lv;
    CustomAdapter customAdapter;
    ArrayList<Weather> weatherArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        anhxa();
        Intent intent=getIntent();
        String city=intent.getStringExtra("name");
        Log.d("Result","Trandmitted Data"+city);
        if(city.equals("")){
            CityName="Saigon";
            get7daysData(CityName);
        } else{
            CityName = city;
            get7daysData(CityName);
        }
    }
    private void anhxa(){
        imgback=(ImageView) findViewById(R.id.imageBack);
        txtName=(TextView) findViewById(R.id.dailyWeather);
        lv=(ListView) findViewById(R.id.listview);
        weatherArrayList = new ArrayList<Weather>();
        customAdapter = new CustomAdapter(MainActivity2.this,weatherArrayList);
    }
    private void get7daysData(String data) {
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q=" + data + "&cnt={cnt}&appid=22de7a243950349ac3f822b7f06b2cb1";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                            String name = jsonObjectCity.getString("name");
                            txtName.setText(name);

                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                            for(int i =0 ;i<jsonArrayList.length();i++){
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                String day = jsonObjectList.getString("dt");
                                long l = Long.valueOf(day);
                                Date date = new Date(1*1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                                String Day= simpleDateFormat.format(date);
                                JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("temp");
                                String max = jsonObjectTemp.getString("max");
                                String min = jsonObjectTemp.getString("min");
                                Double a =Double.valueOf(max);
                                Double b =Double.valueOf(min);
                                String tempMax = String.valueOf(a.intValue());
                                String tempMin = String.valueOf(b.intValue());
                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String status = jsonObjectWeather.getString("description");
                                String icon = jsonObjectWeather.getString("icon");
                                weatherArrayList.add(new Weather(day,status,icon,tempMax,tempMin));

                            }
                            customAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
}
