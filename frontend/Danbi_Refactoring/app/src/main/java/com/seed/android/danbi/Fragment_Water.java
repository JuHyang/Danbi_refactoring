package com.seed.android.danbi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by kkss2 on 2018-04-04.
 */

@SuppressLint("ValidFragment")

public class Fragment_Water extends Fragment {
    Context context;
    private ArrayList<Water_AlarmData> waterDatas;

    private RecyclerView recyclerView_water;
    private RecyclerView.Adapter water_CustomAdapter;
    private RecyclerView.LayoutManager waterLayoutManager;

    public Fragment_Water(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View layout = inflater.inflate(R.layout.fragment_water, container, false);

        InitModel();
        InitView(layout);
        AboutView ();

        return layout;
    }

    public void InitModel () {
        waterDatas = (ArrayList<Water_AlarmData>) Water_AlarmData.listAll(Water_AlarmData.class);
    }

    public void InitView (View view) {
        recyclerView_water = view.findViewById(R.id.recyclerView_water);
        // in content do not change the layout size of the RecyclerView
        recyclerView_water.setHasFixedSize(true);

        // use a linear layout manager
        waterLayoutManager = new LinearLayoutManager(context);
        recyclerView_water.setLayoutManager(waterLayoutManager);

        // specify an adapter (see also next example)
        water_CustomAdapter = new Water_CustomAdapter(waterDatas);
        recyclerView_water.setAdapter(water_CustomAdapter);

    }

    public void AboutView () {
        recyclerView_water.addOnItemTouchListener(new RecyclerItemClickListener(context, recyclerView_water,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // 삭제다이얼로그
                    }
                }));
    }
}
