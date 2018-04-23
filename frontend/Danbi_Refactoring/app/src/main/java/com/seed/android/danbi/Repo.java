package com.seed.android.danbi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kkss2 on 2018-04-23.
 */

public class Repo {
    public ServerInterface getService() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.1:8000")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServerInterface serverInterface = retrofit.create(ServerInterface.class);
        return serverInterface;
    }
}