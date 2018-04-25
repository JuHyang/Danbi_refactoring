package com.seed.android.danbi;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kkss2 on 2018-04-04.
 */

@SuppressLint("ValidFragment")

public class Fragment_Temperature extends Fragment {
    Context context;

    private ArrayList<Temperature_SettingData> temp_datas;
    private Temperature_model temperature_model;
    private Temperature_SettingData temperature_settingData;

    private ImageButton imageButton_refresh;
    private TextView textView_temp, textView_temp_, textView_status_temp, textView_species_temp, textView_proper_temp;
    private TextView textView_hum, textView_hum_, textView_status_hum, textView_species_hum, textView_proper_hum;
    private Switch switch_temp_auto;

    private AlarmManager tempAlarmManager;


    public Fragment_Temperature (Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View layout = inflater.inflate(R.layout.fragment_temperature, container, false);

        InitModel();
        InitVIew(layout);
        AboutView();

        return layout;
    }

    public void InitModel() {
        temp_datas = (ArrayList) Temperature_SettingData.listAll(Temperature_SettingData.class);
        if (temp_datas.size() == 0) {
            temperature_settingData = new Temperature_SettingData(25, 25, false);
            temperature_settingData.save();
        } else {
            temperature_settingData = temp_datas.get(0);
        }
        GetTempData();
    }

    public void InitVIew(View view) {
        imageButton_refresh = view.findViewById(R.id.imageButton_refresh);

        switch_temp_auto = view.findViewById(R.id.switch_temp_auto);

        textView_temp = view.findViewById(R.id.textView_temp);
        textView_temp_ = view.findViewById(R.id.textView_temp_);
        textView_status_temp = view.findViewById(R.id.textView_status_temp);
        textView_species_temp = view.findViewById(R.id.textView_species_temp);
        textView_proper_temp = view.findViewById(R.id.textView_proper_temp);

        textView_hum = view.findViewById(R.id.textView_hum);
        textView_hum_ = view.findViewById(R.id.textView_hum_);
        textView_status_hum = view.findViewById(R.id.textView_status_hum);
        textView_species_hum = view.findViewById(R.id.textView_species_hum);
        textView_proper_hum = view.findViewById(R.id.textView_proper_hum);
    }

    public void AboutView() {
        imageButton_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetTempData();
            }
        });

        switch_temp_auto.setChecked(temperature_settingData.auto);
        switch_temp_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch_temp_auto.isChecked()) {
                    AutoTempGetData();
                    temperature_settingData.auto = true;
                } else {
                    AlarmTempCancel();
                    temperature_settingData.auto = false;
                }
                temperature_settingData.save();
            }
        });
    }

    public void AutoTempGetData () {
        Log.d("확인", "AutoGetData 입장");
        tempAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent (context, Temperature_AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);

        tempAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 3600000, pendingIntent);
//        lmsAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10000, pendingIntent);
    }

    public void AlarmTempCancel() {
        Log.d("확인", "AlarmCancel 입장");
//        AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        tempAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Temperature_AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (sender != null) {
            tempAlarmManager.cancel(sender);
            sender.cancel() ;
        }
    }

    public void GetTempData () {
        ServerInterface serverInterface = new Repo().getService();
        Call<Temperature_model> c= serverInterface.GetTemp();
        c.enqueue(new Callback<Temperature_model>() {
            @Override
            public void onResponse(Call<Temperature_model> call, Response<Temperature_model> response) {
                temperature_model = response.body();
                textView_temp.setText(temperature_model.tem);
                textView_hum.setText(temperature_model.hum);

                if (Integer.valueOf(temperature_model.tem) - temperature_settingData.temperature > 7) {
                    textView_status_temp.setText("온도가 너무 높아요!");
                    textView_status_temp.setTextColor(Color.rgb(230,30,72));
                    textView_temp_.setTextColor(Color.rgb(230,30,72));
                } else if (Integer.valueOf(temperature_model.tem) - temperature_settingData.temperature < -7) {
                    textView_status_temp.setText("온도가 너무 낮아요!");
                    textView_status_temp.setTextColor(Color.rgb(230,30,72));
                    textView_temp_.setTextColor(Color.rgb(230,30,72));
                } else if (Integer.valueOf(temperature_model.tem) - temperature_settingData.temperature > 5) {
                    textView_status_temp.setText("온도가 조금 높아요!");
                    textView_status_temp.setTextColor(Color.rgb(245,166,35));
                    textView_temp_.setTextColor(Color.rgb(245,166,35));
                } else if (Integer.valueOf(temperature_model.tem) - temperature_settingData.temperature < -5) {
                    textView_status_temp.setText("온도가 조금 낮아요!");
                    textView_status_temp.setTextColor(Color.rgb(245,166,35));
                    textView_temp_.setTextColor(Color.rgb(245,166,35));
                }else {
                    textView_status_temp.setText("온도가 적당해요!");
                    textView_status_temp.setTextColor(Color.rgb(85,142,213));
                    textView_temp_.setTextColor(Color.rgb(85,142,213));
                }

                if (Integer.valueOf(temperature_model.hum) - temperature_settingData.humidity > 7) {
                    textView_status_hum.setText("습도가 너무 높아요!");
                    textView_status_hum.setTextColor(Color.rgb(230,30,72));
                    textView_hum_.setTextColor(Color.rgb(230,30,72));
                } else if (Integer.valueOf(temperature_model.hum) - temperature_settingData.humidity < -7) {
                    textView_status_hum.setText("습도가 너무 낮아요!");
                    textView_status_hum.setTextColor(Color.rgb(230,30,72));
                    textView_hum_.setTextColor(Color.rgb(230,30,72));
                } else if (Integer.valueOf(temperature_model.hum) - temperature_settingData.humidity > 5) {
                    textView_status_hum.setText("습도가 조금 높아요!");
                    textView_status_hum.setTextColor(Color.rgb(245,166,35));
                    textView_hum_.setTextColor(Color.rgb(245,166,35));
                } else if (Integer.valueOf(temperature_model.hum) - temperature_settingData.humidity < -5) {
                    textView_status_hum.setText("습도가 조금 낮아요!");
                    textView_status_hum.setTextColor(Color.rgb(245,166,35));
                    textView_hum_.setTextColor(Color.rgb(245,166,35));
                } else {
                    textView_status_hum.setText("습도가 적당해요!");
                    textView_status_hum.setTextColor(Color.rgb(85,142,213));
                    textView_hum_.setTextColor(Color.rgb(85,142,213));
                }
            }

            @Override
            public void onFailure(Call<Temperature_model> call, Throwable t) {
                Toast.makeText(context, "서버에 약간의 오류가 있습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
