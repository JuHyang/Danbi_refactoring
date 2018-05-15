package com.seed.android.danbi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kkss2 on 2018-04-04.
 */

@SuppressLint("ValidFragment")

public class Fragment_Weather extends Fragment {
    private Context context;
    private ImageButton imageButton_location;
    private TextView textView_cityname, textView_currenttemp, textView_currentstatus, textView_currentdetail;
    private ImageView imageView_currentweather;
    private ArrayList<Weather_CityData> cityDatas;
    private Weather_CityData cityData;

    private Weather_GpsInfo gps;
    private Weather_CurrentData currentData;

    private RecyclerView recyclerView_3hour_weather;
    private RecyclerView.Adapter weather_3hour_Adapter;
    private RecyclerView.LayoutManager weather_3hour_LayoutManager;

    private RecyclerView recyclerView_weekly_weather;
    private RecyclerView.Adapter weather_weekly_Adapter;
    private RecyclerView.LayoutManager weather_weekly_LayoutManager;

    public Fragment_Weather (Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View layout = inflater.inflate(R.layout.fragment_weather, container, false);
        InitModel();
        InitView (layout);
        AboutView();

        return layout;
    }

    public void InitModel () {
        cityDatas = (ArrayList) Weather_CityData.listAll(Weather_CityData.class);
        if (cityDatas.size() == 0 ) {
            cityData = new Weather_CityData();
            GetLocation (cityData);
        } else {
            cityData = cityDatas.get(0);
        }
    }

    public void InitView (View view) {
        imageButton_location = view.findViewById(R.id.imageButton_location);
        textView_cityname = view.findViewById(R.id.textView_cityname);
        textView_currenttemp = view.findViewById(R.id.textView_currenttemp);
        textView_currentstatus = view.findViewById(R.id.textView_currentstatus);
        textView_currentdetail = view.findViewById(R.id.textView_currentdetail);
        imageView_currentweather = view.findViewById(R.id.imageView_currentweather);

        recyclerView_3hour_weather = view.findViewById(R.id.recyclerView_3hour_weather);

        recyclerView_weekly_weather = view.findViewById(R.id.recyclerView_weekly_weather);
    }

    public void AboutView () {
        GetCurrentWeather ();

        imageButton_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetLocation (cityData);
                GetCurrentWeather ();
            }
        });

    }

    public void GetCurrentWeather () {
        ServerInterface serverInterface = new Weather_Repo().getService();
        Call<Weather_CurrentData> c = serverInterface.CurrentWeahter(cityData.lat, cityData.lon);
        c.enqueue(new Callback<Weather_CurrentData>() {
            @Override
            public void onResponse(Call<Weather_CurrentData> call, Response<Weather_CurrentData> response) {
                currentData = response.body();
                currentData.setWind();
                currentData.setWeather();
                textView_cityname.setText(currentData.name);
                textView_currenttemp.setText(String.valueOf(currentData.main.temp) + "°C");
                textView_currentstatus.setText(currentData.weather_main);
                String detail = "풍속 " + String.valueOf(currentData.wind.speed) + "m/s" +
                        "풍향 " + currentData.wind_deg + "습도 " + String.valueOf(currentData.main.humidity) + "%";
                textView_currentdetail.setText(detail);
                imageView_currentweather.setImageResource(currentData.image_weather);
            }

            @Override
            public void onFailure(Call<Weather_CurrentData> call, Throwable t) {

            }
        });
    }

    public void GetLocation (Weather_CityData cityData) {
        gps = new Weather_GpsInfo(context);

        // GPS 사용유무 가져오기
        if (gps.isGetLocation()) {
            cityData.lat = gps.getLatitude();
            cityData.lon = gps.getLongitude();

            cityData.save();
        } else {
            // GPS 를 사용할수 없으므로
            gps.showSettingsAlert();
        }
    }

}
