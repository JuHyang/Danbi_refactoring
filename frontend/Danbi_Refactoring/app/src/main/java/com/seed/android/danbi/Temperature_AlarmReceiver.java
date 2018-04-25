package com.seed.android.danbi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kkss2 on 2018-04-04.
 */

public class Temperature_AlarmReceiver extends BroadcastReceiver{
    private NotificationManager notificationManager;
    private Temperature_SettingData temperature_settingData;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("확인", "AlarmReceiver 입장");
        ArrayList<Temperature_SettingData> temp_Datas = (ArrayList) Temperature_SettingData.listAll(Temperature_SettingData.class);
        temperature_settingData = temp_Datas.get(0);
        if (temperature_settingData.auto) {
            GetTempData (context);
        }
    }

    public void GetTempData (final Context context) {
        ServerInterface serverInterface = new Repo().getService();
        Call<Temperature_model> c= serverInterface.GetTemp();
        c.enqueue(new Callback<Temperature_model>() {
            @Override
            public void onResponse(Call<Temperature_model> call, Response<Temperature_model> response) {
                Temperature_model temperature_model;
                temperature_model = response.body();
                String message = "";

                if (Integer.valueOf(temperature_model.tem) - temperature_settingData.temperature > 7) {
                    message = "온도가 너무 높아요!";
                } else if (Integer.valueOf(temperature_model.tem) - temperature_settingData.temperature < -7) {
                    message = "온도가 너무 낮아요!";
                } else if (Integer.valueOf(temperature_model.tem) - temperature_settingData.temperature > 5) {
                    message = "온도가 조금 높아요!";
                } else if (Integer.valueOf(temperature_model.tem) - temperature_settingData.temperature < -5) {
                    message = "온도가 조금 낮아요!";
                }

                if (message.equals("")) {
                    GenerateNotification(context, message, 111);
                }

                message = "";

                if (Integer.valueOf(temperature_model.hum) - temperature_settingData.humidity > 7) {
                    message = "습도가 너무 높아요!";
                } else if (Integer.valueOf(temperature_model.hum) - temperature_settingData.humidity < -7) {
                    message = "습도가 너무 낮아요!";
                } else if (Integer.valueOf(temperature_model.hum) - temperature_settingData.humidity > 5) {
                    message = "습도가 조금 높아요!";
                } else if (Integer.valueOf(temperature_model.hum) - temperature_settingData.humidity < -5) {
                    message = "습도가 조금 낮아요!";
                }
                if (message.equals("")) {
                    GenerateNotification (context, message, 222);
                }
            }

            @Override
            public void onFailure(Call<Temperature_model> call, Throwable t) {
            }
        });
    }

    public void GenerateNotification (Context context, String message, int id) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 , new Intent(context, SplashActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.push_alarm_icon));
        builder.setContentTitle("단비");
        builder.setContentText(message);
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        builder.setContentIntent(pendingIntent);
        builder.setPriority(Notification.PRIORITY_MAX);

        notificationManager.notify(id, builder.build());
    }
}
