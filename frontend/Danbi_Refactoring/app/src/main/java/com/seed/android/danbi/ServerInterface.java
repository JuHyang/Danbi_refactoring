package com.seed.android.danbi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by kkss2 on 2018-04-04.
 */

public interface ServerInterface {
    @GET("/Water/{onoff}/{minute}")
    Call<String> Watering(@Path("onoff") boolean onoff,
                              @Path("during") int during);

    @GET("/Tem")
    Call<Temperature_model> GetTemp ();

    @GET("/door/{onoff}")
    Call<Door_model> DoorOpen(@Path("onoff") String onoff);
}
