package com.seed.android.danbi;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class Water_Register extends AppCompatActivity {

    private TimePicker timePicker;
    private Switch switch_register;
    private CheckBox checkBox_repeat, checkbox_before_5min, checkbox_water_finish;
    private TextView textView_repeat;
    private SeekBar seekBar_water_min;
    private RadioButton radioButton;

    private Toolbar toolbar;
    private ImageButton imageButton_register_back;
    private TextView textView_register_save;

    private int hour_timePicker, minute_timePicker, during_seekBar;
    private boolean bool_register, bool_repeat, bool_before_5min, bool_water_finish;

    private Water_AlarmData water_alarmData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_register);

        InitView();
        SetCustomActionBar();
        AboutView();

    }

    public void InitView () {
        timePicker = findViewById(R.id.timePicker);
        switch_register = findViewById(R.id.switch_register);
        checkBox_repeat = findViewById(R.id.checkBox_repeat);
        checkbox_before_5min = findViewById(R.id.checkbox_before_5min);
        checkbox_water_finish = findViewById(R.id.checkbox_water_finish);
        textView_repeat = findViewById(R.id.textView_repeat);
        seekBar_water_min = findViewById(R.id.seekBar_water_min);
        radioButton = findViewById(R.id.radioButton);


    }

    public void AboutView () {

//        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//                hour_timePicker = hourOfDay;
//                minute_timePicker = minute;
//            }
//        });

        bool_register = switch_register.isChecked();
//        switch_register.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                bool_register = isChecked;
//            }
//        });
        bool_repeat = checkBox_repeat.isChecked();
        bool_before_5min = checkbox_before_5min.isChecked();
        bool_water_finish = checkbox_water_finish.isChecked();

        textView_repeat.setText(String.valueOf(seekBar_water_min.getProgress()));
        during_seekBar = seekBar_water_min.getProgress();

        seekBar_water_min.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView_repeat.setText(String.valueOf(progress));
                during_seekBar = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkbox_before_5min.setChecked(false);
                    checkbox_water_finish.setChecked(false);
                }
            }
        });

        imageButton_register_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textView_register_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour_timePicker = timePicker.getCurrentHour();
                minute_timePicker = timePicker.getCurrentMinute();
                water_alarmData = new Water_AlarmData(hour_timePicker, minute_timePicker, during_seekBar, bool_register, bool_repeat);
                water_alarmData.save();
                Watering();
                finish();
            }
        });

    }

    public void Watering () {
        AlarmManager waterAlarmManager;
        waterAlarmManager = (AlarmManager) Water_Register.this.getSystemService(Context.ALARM_SERVICE);
        long id = water_alarmData.getId();
        Intent intent = new Intent(Water_Register.this, Water_AlarmReceiver.class);
        intent.putExtra("id", id);
        PendingIntent pending = PendingIntent.getBroadcast(Water_Register.this, (int) id, intent, 0); //getPendingIntent 라는 method에 보내줌
        if (water_alarmData.repeat) {
            waterAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, SetTriggerTime(water_alarmData), 3600000, pending);
        } else {
            waterAlarmManager.set(AlarmManager.RTC_WAKEUP, SetTriggerTime(water_alarmData), pending);
        }
    }

    private long SetTriggerTime (Water_AlarmData water_alarmData) {
        // current Time
        long atime = System.currentTimeMillis(); //현재시간 저장
        // timepicker
        Calendar curTime = Calendar.getInstance(); //캘린더 변수 생성
        curTime.set(Calendar.HOUR_OF_DAY, water_alarmData.hour); //캘린더에 시간 저장
        curTime.set(Calendar.MINUTE, water_alarmData.minute); //캘린더에 분 저장
        curTime.set(Calendar.SECOND, 0); //초, millisecond 0으로 넣음
        curTime.set(Calendar.MILLISECOND, 0);
        long btime = curTime.getTimeInMillis();  //calendar를 btime에 저장
        long triggerTime = btime; //b time 을 triggertime에 저장
        if (atime > btime) //크기 비교해서
            triggerTime += 1000 * 60 * 60 * 24; //현재시간이 더 나중일 경우에 시작시간에 하루를 더함

        return triggerTime;
    }

    private void SetCustomActionBar () {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        View actionBarView = LayoutInflater.from(this).inflate(R.layout.actionbar_water_register, null);
        imageButton_register_back = actionBarView.findViewById(R.id.imageButton_register_back);
        textView_register_save = actionBarView.findViewById(R.id.textView_register_save);
        actionBar.setCustomView(actionBarView);

        toolbar = (Toolbar) actionBarView.getParent();
        toolbar.setContentInsetsAbsolute(0,0);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(actionBarView, params);

    }
}
