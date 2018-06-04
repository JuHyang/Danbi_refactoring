package com.seed.android.danbi;

import android.content.Context;
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

public class Weather_3hour_CustomAdapter extends RecyclerView.Adapter<Weather_3hour_CustomAdapter.ViewHolder> {
    ArrayList<Weather_3hour_listData> weather_3hourDatas;
    Context context;

    public Weather_3hour_CustomAdapter (ArrayList<Weather_3hour_listData> weather_3hourDatas, Context context) {
        this.weather_3hourDatas = weather_3hourDatas;
        this.context = context;
    }

    @Override
    public Weather_3hour_CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_3hour_weather, parent, false);
        // set the view's size, margins, paddings and layout parameters

        Weather_3hour_CustomAdapter.ViewHolder vh = new Weather_3hour_CustomAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final Weather_3hour_CustomAdapter.ViewHolder holder, final int position) {
        final Weather_3hour_listData temp = weather_3hourDatas.get(position);
        holder.textView_temp_3hour.setText(temp.temp);
        holder.imageView_3hour_weather.setImageResource(temp.img);
        holder.textView_time_3hour.setText(temp.time);
    }

    @Override
    public int getItemCount() {
        return weather_3hourDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_temp_3hour;
        ImageView imageView_3hour_weather;
        TextView textView_time_3hour;
        public ViewHolder(View itemView) {
            super(itemView);
            textView_temp_3hour = itemView.findViewById(R.id.textView_temp_3hour);
            imageView_3hour_weather = itemView.findViewById(R.id.imageView_3hour_weather);
            textView_time_3hour = itemView.findViewById(R.id.textView_time_3hour);

        }
    }
}
