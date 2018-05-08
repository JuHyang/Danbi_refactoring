package com.seed.android.danbi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kkss2 on 2018-05-08.
 */

public class Weather_Repo {
    public ServerInterface getService() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://http://api.openweathermap.org")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServerInterface serverInterface = retrofit.create(ServerInterface.class);
        return serverInterface;
    }
}
