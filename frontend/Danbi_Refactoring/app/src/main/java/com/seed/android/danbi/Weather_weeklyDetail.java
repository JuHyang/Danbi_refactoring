package com.seed.android.danbi;

import java.util.ArrayList;

/**
 * Created by kkss2 on 2018-06-04.
 */

public class Weather_weeklyDetail {
    long dt;
    Weather_Temp temp;
    ArrayList<Weather_id> weather;

    int image_weather;

    public void setWeather () {
        int id = weather.get(0).id;

        if (id == 800 || 700 < id && id < 761) {
            image_weather = R.drawable.sun_blue;
        }
        else if (500 <= id && id < 600) {
            image_weather = R.drawable.rain_blue;
        }
        else if (300 <= id && id < 400) {
            image_weather = R.drawable.rain_blue; //변경 가능성 있음
        }
        else if (200 <= id && id < 300) {
            image_weather = R.drawable.thunder_storm_blue;
        }
        else if (600 <= id && id < 700) {
            image_weather = R.drawable.snow_blue;
        }
        else if (800 < id && id < 900) {
            image_weather = R.drawable.cloud_blue;
        }
        else if (id == 903 || id == 904 || id == 905) {
            image_weather = R.drawable.sun_blue; // 미상
        }
        else if (900 <= id && id < 900) {
            image_weather = R.drawable.sun_blue; //변경 가능성 있음
        }
    }
}
