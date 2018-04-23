package com.seed.android.danbi;

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
        imageButton_register_back = findViewById(R.id.imageButton_register_back);
        textView_register_save = findViewById(R.id.textView_register_save);
    }

    public void AboutView () {
        hour_timePicker = timePicker.getCurrentHour();
        minute_timePicker = timePicker.getCurrentMinute();
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hour_timePicker = hourOfDay;
                minute_timePicker = minute;
            }
        });

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
                water_alarmData = new Water_AlarmData(hour_timePicker, minute_timePicker, during_seekBar, bool_register, bool_repeat);
                water_alarmData.save();
                //알람 등록
                finish();
            }
        });

    }

    private void SetCustomActionBar () {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        View actionBarView = LayoutInflater.from(this).inflate(R.layout.actionbar_water_register, null);
        actionBar.setCustomView(actionBarView);

        toolbar = (Toolbar) actionBarView.getParent();
        toolbar.setContentInsetsAbsolute(0,0);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(actionBarView, params);

    }
}
