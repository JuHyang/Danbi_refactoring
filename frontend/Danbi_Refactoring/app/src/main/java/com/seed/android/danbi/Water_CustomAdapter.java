package com.seed.android.danbi;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by kkss2 on 2018-04-04.
 */

public class Water_CustomAdapter extends RecyclerView.Adapter<Water_CustomAdapter.ViewHolder> {
    private ArrayList<Water_AlarmData> waterDatas;
    private Context context;

    private AlarmManager waterAlarmManager;


    public Water_CustomAdapter (ArrayList<Water_AlarmData> waterDatas, Context context) {
        this.waterDatas = waterDatas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_water, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Water_AlarmData temp = waterDatas.get(position);
        String time;
        if (temp.hour >= 12) {
            holder.textView_am.setText("오후");
            if (temp.hour == 12) {
                time = "12";
            } else {
                time = String.valueOf(temp.hour - 12);
            }
        } else {
            holder.textView_am.setText("오전");
            time = String.valueOf(temp.hour);
        }
        time = time + ":" + String.valueOf(temp.minute);
        holder.textView_time.setText(time);
        holder.textVIew_min.setText(temp.during);
        if (temp.repeat) {
            holder.imageButton_repeat.setImageResource(R.drawable.alarm_everyday_icon_bk);
        } else {
            holder.imageButton_repeat.setImageResource(R.drawable.alarm_everyday_icon_gray);
        }
        if (temp.onoff) {
            holder.textView_am.setTextColor(Color.BLACK);
            holder.textView_time.setTextColor(Color.BLACK);
            holder.textVIew_min.setTextColor(Color.BLACK);
        } else {
            holder.textView_am.setTextColor(Color.rgb(190,190,190));
            holder.textView_time.setTextColor(Color.rgb(190,190,190));
            holder.imageButton_repeat.setImageResource(R.drawable.alarm_everyday_icon_gray);
            holder.textVIew_min.setTextColor(Color.rgb(190,190,190));
        }
        holder.switch_on.setChecked(temp.onoff);
        if (temp.onoff) {
            holder.imageButton_repeat.setOnClickListener(new View.OnClickListener() {
                // 알람수정
                @Override
                public void onClick(View v) {
                    if (temp.repeat) {
                        holder.imageButton_repeat.setImageResource(R.drawable.alarm_everyday_icon_gray);
                    } else {
                        holder.imageButton_repeat.setImageResource(R.drawable.alarm_everyday_icon_bk);
                    }
                }
            });
        }

        holder.switch_on.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Watering(position);
                if (isChecked) {
                    holder.textView_am.setTextColor(Color.BLACK);
                    holder.textView_time.setTextColor(Color.BLACK);
                    holder.textVIew_min.setTextColor(Color.BLACK);
                    if (temp.repeat) {
                        holder.imageButton_repeat.setImageResource(R.drawable.alarm_everyday_icon_bk);
                    }
                } else {
                    WaterCancel(position);
                    holder.textView_am.setTextColor(Color.rgb(190,190,190));
                    holder.textView_time.setTextColor(Color.rgb(190,190,190));
                    holder.imageButton_repeat.setImageResource(R.drawable.alarm_everyday_icon_gray);
                    holder.textVIew_min.setTextColor(Color.rgb(190,190,190));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return waterDatas.size();
    }

    public void Watering (int position) {
        waterAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Water_AlarmData water_alarmData = waterDatas.get(position);
        long id = water_alarmData.getId();
        Intent intent = new Intent(context, Water_AlarmReceiver.class);
        intent.putExtra("id", id);
        PendingIntent pending = PendingIntent.getBroadcast(context, (int) id, intent, 0); //getPendingIntent 라는 method에 보내줌
        if (water_alarmData.repeat) {
            waterAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, SetTriggerTime(water_alarmData), 3600000, pending);
        } else {
            waterAlarmManager.set(AlarmManager.RTC_WAKEUP, SetTriggerTime(water_alarmData), pending);
        }

    }

    public void WaterCancel (int position) {
        Water_AlarmData water_alarmData = waterDatas.get(position);
        long id = water_alarmData.getId();
        waterAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Water_AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, (int) id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (sender != null) {
            waterAlarmManager.cancel(sender);
            sender.cancel() ;
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


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_am;
        TextView textView_time;
        ImageButton imageButton_repeat;
        TextView textVIew_min;
        Switch switch_on;
        public ViewHolder(View itemView) {
            super(itemView);
            textView_am = itemView.findViewById(R.id.textView_am);
            textView_time = itemView.findViewById(R.id.textView_time);
            imageButton_repeat = itemView.findViewById(R.id.imageButton_repeat);
            textVIew_min = itemView.findViewById(R.id.textVIew_min);
            switch_on = itemView.findViewById(R.id.switch_on);
        }
    }
}
