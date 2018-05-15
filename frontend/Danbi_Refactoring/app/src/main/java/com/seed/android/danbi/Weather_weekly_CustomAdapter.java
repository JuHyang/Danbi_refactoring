package com.seed.android.danbi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kkss2 on 2018-05-08.
 */

public class Weather_weekly_CustomAdapter extends RecyclerView.Adapter<Weather_weekly_CustomAdapter.ViewHolder> {
    ArrayList<Weather_weeklyData> weather_weeklyDatas;
    Context context;

    public Weather_weekly_CustomAdapter (ArrayList<Weather_weeklyData> weather_weeklyDatas, Context context) {
        this.weather_weeklyDatas = weather_weeklyDatas;
        this.context = context;
    }

    @Override
    public Weather_weekly_CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_water, parent, false);
        // set the view's size, margins, paddings and layout parameters

        Weather_weekly_CustomAdapter.ViewHolder vh = new Weather_weekly_CustomAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final Weather_weekly_CustomAdapter.ViewHolder holder, final int position) {
        final Weather_weeklyData temp = weather_weeklyDatas.get(position);
    }

    @Override
    public int getItemCount() {
        return weather_weeklyDatas.size();
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
