package com.seed.android.danbi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

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

    @GET("/data/2.5/weather?lat={lat}&lon={lon}&appid=8cbb933c3116dd59f65e17923de6240e&units=metric")
    Call<Weather_CurrentData> CurrentWeahter (@Path ("lat") double lat, @Path ("lon") double lon);

    @GET("/data/2.5/forecast?lat={lat}&lon={lon}&appid=8cbb933c3116dd59f65e17923de6240e&units=metric&cnt=8")
    Call<Weather_CurrentData> HourWeather (@Path ("lat") double lat, @Path ("lon") double lon);

    @GET("/data/2.5/forecast/daily?lat={lat}&lon={lon}&appid=8cbb933c3116dd59f65e17923de6240e&units=metric&cnt=7")
    Call<Weather_CurrentData> WeeklyWeather (@Path ("lat") double lat, @Path ("lon") double lon);
}
