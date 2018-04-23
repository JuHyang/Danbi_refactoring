package com.seed.android.danbi;

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

/**
 * Created by kkss2 on 2018-04-04.
 */

public class Water_CustomAdapter extends RecyclerView.Adapter<Water_CustomAdapter.ViewHolder> {
    private ArrayList<Water_AlarmData> waterDatas;

    public Water_CustomAdapter (ArrayList<Water_AlarmData> waterDatas) {
        this.waterDatas = waterDatas;
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
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
                //알람 생기기
                if (isChecked) {
                    holder.textView_am.setTextColor(Color.BLACK);
                    holder.textView_time.setTextColor(Color.BLACK);
                    holder.textVIew_min.setTextColor(Color.BLACK);
                    if (temp.repeat) {
                        holder.imageButton_repeat.setImageResource(R.drawable.alarm_everyday_icon_bk);
                    }
                } else {
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
