package com.seed.android.danbi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kkss2 on 2018-04-04.
 */

@SuppressLint("ValidFragment")

public class Fragment_Weather extends Fragment {

    private String appid = "8cbb933c3116dd59f65e17923de6240e";
    private String units = "metric";

    private Context context;
    private ImageButton imageButton_location;
    private TextView textView_cityname, textView_currenttemp, textView_currentstatus, textView_currentdetail;
    private ImageView imageView_currentweather;
    private ArrayList<Weather_CityData> cityDatas;
    private Weather_CityData cityData;

    private Weather_GpsInfo gps;
    private Weather_CurrentData currentData;

    private Weather_3hourData hourData;
    private ArrayList<Weather_3hour_listData> hour_listDatas = new ArrayList<>();
    private RecyclerView recyclerView_3hour_weather;
    private RecyclerView.Adapter weather_3hour_Adapter;
    private RecyclerView.LayoutManager weather_3hour_LayoutManager;

    private Weather_weeklyData weeklyData;
    private ArrayList<Weather_weekly_listData> weekly_listDatas = new ArrayList<>();
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
        CheckPermission();
        AboutView();

        return layout;
    }

    public void onResume() {
        super.onResume();
        InitModel();
        weather_3hour_Adapter.notifyDataSetChanged();
        weather_weekly_Adapter.notifyDataSetChanged();
        AboutView();
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
        recyclerView_3hour_weather.setHasFixedSize(true);
        weather_3hour_LayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView_3hour_weather.setLayoutManager(weather_3hour_LayoutManager);
        weather_3hour_Adapter = new Weather_3hour_CustomAdapter(hour_listDatas, context);
        recyclerView_3hour_weather.setAdapter(weather_3hour_Adapter);

        recyclerView_weekly_weather = view.findViewById(R.id.recyclerView_weekly_weather);
        recyclerView_weekly_weather.setHasFixedSize(true);
        weather_weekly_LayoutManager = new LinearLayoutManager(context);
        recyclerView_weekly_weather.setLayoutManager(weather_weekly_LayoutManager);
        weather_weekly_Adapter = new Weather_weekly_CustomAdapter(weekly_listDatas, context);
        recyclerView_weekly_weather.setAdapter(weather_weekly_Adapter);
    }

    public void AboutView () {
        GetCurrentWeather ();
        Get3HourWeather();
        GetWeeklyWeather();

        imageButton_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetLocation (cityData);
                GetCurrentWeather ();
                Get3HourWeather();
                GetWeeklyWeather();
            }
        });

    }

    public void CheckPermission () {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
//                Toast.makeText(context, "권한 허가", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
//                Toast.makeText(context,"권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.with(context)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("날씨를 확인하기 위해서 위치 권한이 필요합니다.")
                .setDeniedMessage("설정] > [권한] 에서 권한을 허용할 수 있습니다.")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check();
    }

    public void GetCurrentWeather () {
        ServerInterface serverInterface = new Weather_Repo().getService();
        Call<Weather_CurrentData> c = serverInterface.CurrentWeahter(cityData.lat, cityData.lon, appid, units);
        c.enqueue(new Callback<Weather_CurrentData>() {
            @Override
            public void onResponse(Call<Weather_CurrentData> call, Response<Weather_CurrentData> response) {
                currentData = response.body();
                currentData.setWind();
                currentData.setWeather();
                textView_cityname.setText(currentData.name);
                textView_currenttemp.setText(String.valueOf(currentData.main.temp) + "°C");
                textView_currentstatus.setText(currentData.weather_main);
                String detail = "풍속 : " + String.valueOf(currentData.wind.speed) + "m/s" +
                        "  풍향 : " + currentData.wind_deg + "  습도 : " + String.valueOf(currentData.main.humidity) + "%";
                textView_currentdetail.setText(detail);
                imageView_currentweather.setImageResource(currentData.image_weather);

            }

            @Override
            public void onFailure(Call<Weather_CurrentData> call, Throwable t) {
                Log.d("태그", t.getMessage());
            }
        });
    }

    public void Get3HourWeather () {
        ServerInterface serverinterface = new Weather_Repo().getService();
        Call<Weather_3hourData> c = serverinterface.HourWeather(cityData.lat, cityData.lon, appid, units, 8);
        c.enqueue(new Callback<Weather_3hourData>() {
            @Override
            public void onResponse(Call<Weather_3hourData> call, Response<Weather_3hourData> response) {
                hour_listDatas.clear();
                hourData = response.body();
                String temptime;
                for (int i = 0; i < 8; i ++) {
                    Weather_3hour_listData listData = new Weather_3hour_listData();
                    hourData.list.get(i).setWeather();
                    listData.temp = String.valueOf(hourData.list.get(i).main.temp.intValue()) + "°C";
                    listData.img = hourData.list.get(i).image_weather;
                    temptime = hourData.list.get(i).dt_txt;
                    int time = Integer.parseInt(temptime.split(" ")[1].split(":")[0]) + 8;
                    if (time >= 24) {
                        time = time - 24;
                    }
                    listData.time = String.valueOf(time) + "시";

                    hour_listDatas.add(listData);
                    weather_3hour_Adapter.notifyDataSetChanged();
                }
                Log.d("날씨", "3시간날씨");
                Log.d("hour_listDatas", String.valueOf(hour_listDatas.size()));
            }

            @Override
            public void onFailure(Call<Weather_3hourData> call, Throwable t) {
                Log.d("태그", t.getMessage());
            }
        });
        weather_3hour_Adapter.notifyDataSetChanged();
    }

    public void GetWeeklyWeather () {
        ServerInterface serverinterface = new Weather_Repo().getService();
        Call<Weather_weeklyData> c = serverinterface.WeeklyWeather(cityData.lat, cityData.lon, appid, units, 7);
        c.enqueue(new Callback<Weather_weeklyData>() {
            @Override
            public void onResponse(Call<Weather_weeklyData> call, Response<Weather_weeklyData> response) {
                weekly_listDatas.clear();
                weeklyData = response.body();
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_WEEK); //일요일은 1 토요일은 7
                String day_str;
                for (int i = 0; i < 7; i ++) {
                    if (day > 7) {
                        day = 1;
                    }
                    day_str = new String();
                    Weather_weekly_listData listData = new Weather_weekly_listData();
                    if (day == 1) {
                        day_str = "일";
                    } else if (day == 2) {
                        day_str = "월";
                    } else if (day == 3) {
                        day_str = "화";
                    } else if (day == 4) {
                        day_str = "수";
                    } else if (day == 5) {
                        day_str = "목";
                    } else if (day == 6) {
                        day_str = "금";
                    } else if (day == 7) {
                        day_str = "토";
                    }
                    listData.day = day_str;
                    weeklyData.list.get(i).setWeather();
                    listData.temp = String.valueOf(weeklyData.list.get(i).temp.max.intValue())  + "°C / "
                            + String.valueOf(weeklyData.list.get(i).temp.min.intValue()) + "°C" ;
                    listData.img = weeklyData.list.get(i).image_weather;

                    weekly_listDatas.add(listData);
                    weather_weekly_Adapter.notifyDataSetChanged();
                    day = day + 1;
                }
                Log.d("날씨", "주간날씨");
                Log.d("weekly_listDatas", String.valueOf(weekly_listDatas.size()));
            }

            @Override
            public void onFailure(Call<Weather_weeklyData> call, Throwable t) {
                Log.d("태그", t.getMessage());
            }
        });
        weather_weekly_Adapter.notifyDataSetChanged();
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
