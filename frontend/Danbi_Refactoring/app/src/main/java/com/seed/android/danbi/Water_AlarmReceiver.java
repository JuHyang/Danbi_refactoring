package com.seed.android.danbi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kkss2 on 2018-04-04.
 */

public class Water_AlarmReceiver extends BroadcastReceiver{

    private Bundle extra;
    private long id;

    private ArrayList<Water_AlarmData> water_alarmDatas;
    private Water_AlarmData target;

    @Override
    public void onReceive(Context context, Intent intent) {
        InitModel (intent);
        Watering();
    }

    public void InitModel (Intent intent) {
        water_alarmDatas = (ArrayList) Water_AlarmData.listAll(Water_AlarmData.class);
        extra = intent.getExtras();
        id = extra.getLong("id");
        for (int i = 0; i < water_alarmDatas.size(); i ++) {
            Water_AlarmData temp = water_alarmDatas.get(i);
            if (temp.getId() == id) {
                target = temp;
                break;
            }
        }
    }

    public void Watering () {
        final ServerInterface serverInterface = new Repo().getService();
        Call<String> c = serverInterface.Watering(target.onoff, target.during);
        final CountDownTimer[] timer = new CountDownTimer[1];
        c.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("1")) {
                        timer[0] = new CountDownTimer(target.during * 60 * 1000, 1000) {
                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                Call<String> c = serverInterface.Watering(false, 0);
                                c.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        if (response.body().equals("1")) {
                                            timer[0].cancel();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {

                                    }
                                });
                            }
                        };
                    }
                }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
