package com.seed.android.danbi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.design.widget.FloatingActionButton;
import com.orm.query.Select;

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

    private FloatingActionButton fab;

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

    public void onResume () {
        super.onResume();

        InitModel();
        water_CustomAdapter.notifyDataSetChanged();
        AboutView();
    }

    public void InitModel () {
        waterDatas = (ArrayList<Water_AlarmData>) Water_AlarmData.listAll(Water_AlarmData.class);
        if (waterDatas.size() != 0) {
            waterDatas = (ArrayList<Water_AlarmData>) Select.from(Water_AlarmData.class).orderBy("hour, minute").list();
        }
    }

    public void InitView (View view) {
        recyclerView_water = view.findViewById(R.id.recyclerView_water);
        // in content do not change the layout size of the RecyclerView
        recyclerView_water.setHasFixedSize(true);

        // use a linear layout manager
        waterLayoutManager = new LinearLayoutManager(context);
        recyclerView_water.setLayoutManager(waterLayoutManager);

        // specify an adapter (see also next example)
        water_CustomAdapter = new Water_CustomAdapter(waterDatas, context);
        recyclerView_water.setAdapter(water_CustomAdapter);

        fab = view.findViewById(R.id.fab);
    }

    public void AboutView () {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (context, Water_Register.class);
                startActivity(intent);
            }
        });
        recyclerView_water.addOnItemTouchListener(new RecyclerItemClickListener(context, recyclerView_water,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        OpenDeleteDialog(position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        OpenDeleteDialog(position);
                    }
                }));
    }

    public void OpenDeleteDialog (final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("삭제");
        builder.setMessage("삭제 하시겠습니까 ?");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Water_AlarmData temp = waterDatas.get(position);
                temp.delete();
                water_CustomAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }
}
