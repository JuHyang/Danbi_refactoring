package com.seed.android.danbi;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kkss2 on 2018-05-08.
 */

public class Weather_weekly_CustomAdapter extends RecyclerView.Adapter<Weather_weekly_CustomAdapter.ViewHolder> {
    ArrayList<Weather_weekly_listData> weather_weeklyDatas;
    Context context;

    public Weather_weekly_CustomAdapter (ArrayList<Weather_weekly_listData> weather_weeklyDatas, Context context) {
        this.weather_weeklyDatas = weather_weeklyDatas;
        this.context = context;
    }

    @Override
    public Weather_weekly_CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weekly_weather, parent, false);
        // set the view's size, margins, paddings and layout parameters

        Weather_weekly_CustomAdapter.ViewHolder vh = new Weather_weekly_CustomAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final Weather_weekly_CustomAdapter.ViewHolder holder, final int position) {
        final Weather_weekly_listData temp = weather_weeklyDatas.get(position);
        holder.textView_weekly_day.setText(temp.day);
        holder.imageView_weekly.setImageResource(temp.img);
        holder.textView_weekly_temp.setText(temp.temp);
        if (position == 0) {
            holder.textView_weekly_day.setTextColor(Color.rgb(105,174,255));
            holder.textView_weekly_temp.setTextColor(Color.rgb(105,174,255));
        }
    }

    @Override
    public int getItemCount() {
        return weather_weeklyDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_weekly_day;
        ImageView imageView_weekly;
        TextView textView_weekly_temp;
        public ViewHolder(View itemView) {
            super(itemView);
            textView_weekly_day = itemView.findViewById(R.id.textView_weekly_day);
            imageView_weekly = itemView.findViewById(R.id.imageView_weekly);
            textView_weekly_temp = itemView.findViewById(R.id.textView_weekly_temp);

        }
    }
}
