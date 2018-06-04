package com.seed.android.danbi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by kkss2 on 2018-04-04.
 */

public interface ServerInterface {
    @GET("/water/{onoff}/{minute}")
    Call<String> Watering(@Path("onoff") boolean onoff,
                              @Path("during") int during);

    @GET("/temp")
    Call<Temperature_model> GetTemp ();

    @GET("/door/{onoff}")
    Call<Door_model> DoorOpen(@Path("onoff") String onoff);

    @GET("/data/2.5/weather")
    Call<Weather_CurrentData> CurrentWeahter (@Query("lat") double lat, @Query ("lon") double lon, @Query("appid") String appid, @Query ("units") String units);

    @GET("/data/2.5/forecast?&appid=8cbb933c3116dd59f65e17923de6240e&units=metric&cnt=8")
    Call<Weather_3hourData> HourWeather (@Query ("lat") double lat, @Query ("lon") double lon);

    @GET("/data/2.5/forecast/daily?&appid=8cbb933c3116dd59f65e17923de6240e&units=metric&cnt=7")
    Call<Weather_weeklyData> WeeklyWeather (@Query ("lat") double lat, @Query ("lon") double lon);
}
