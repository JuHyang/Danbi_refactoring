package com.seed.android.danbi;

import java.util.ArrayList;

/**
 * Created by kkss2 on 2018-05-08.
 */

public class Weather_CurrentData {
    Weather_Main main;
    Weather_Wind wind;
    ArrayList<Weather_id> weather;
    String name;
    String dt_txt;

    String wind_deg;
    String weather_main;
    int image_weather;

    public void setWeather () {
        int id = weather.get(0).id;

        if (id == 800 || 700 < id && id < 761) {
            weather_main = "맑음";
            image_weather = R.drawable.sun_blue;
        }
        else if (500 <= id && id < 600) {
            weather_main = "비";
            image_weather = R.drawable.rain_blue;
        }
        else if (300 <= id && id < 400) {
            weather_main = "약한 비";
            image_weather = R.drawable.rain_blue; //변경 가능성 있음
        }
        else if (200 <= id && id < 300) {
            weather_main = "천둥번개";
            image_weather = R.drawable.thunder_storm_blue;
        }
        else if (600 <= id && id < 700) {
            weather_main = "눈";
            image_weather = R.drawable.snow_blue;
        }
        else if (800 < id && id < 900) {
            weather_main = "구름";
            image_weather = R.drawable.cloud_blue;
        }
        else if (id == 903 || id == 904 || id == 905) {
            weather_main = "미상";
            image_weather = R.drawable.sun_blue;
        }
        else if (900 <= id && id < 900) {
            weather_main = "태풍";
            image_weather = R.drawable.sun_blue; //변경 가능성 있음
        }
    }

    public void setWind () {
        int intDegree = wind.deg.intValue();
        if (intDegree == 0 || intDegree == 360)
            wind_deg = "북";
        else if (intDegree == 90)
            wind_deg = "동";
        else if (intDegree == 180)
            wind_deg = "남";
        else if (intDegree == 270)
            wind_deg = "서";
        else if (0 < intDegree && intDegree < 90)
            wind_deg = "북동";
        else if (90 < intDegree && intDegree < 180)
            wind_deg = "남동";
        else if (180 < intDegree && intDegree < 270)
            wind_deg = "남서";
        else
            wind_deg = "북서";
    }

    public Weather_Main getMain() {
        return main;
    }


}
