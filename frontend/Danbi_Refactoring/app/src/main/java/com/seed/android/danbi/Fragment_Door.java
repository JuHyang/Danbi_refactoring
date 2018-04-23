package com.seed.android.danbi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kkss2 on 2018-04-04.
 */

@SuppressLint("ValidFragment")

public class Fragment_Door extends Fragment {
    Context context;

    private ImageButton imageButton_door_open, imageButton_door_close;

    public Fragment_Door (Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View layout = inflater.inflate(R.layout.fragment_door, container, false);

        InitView(layout);
        AboutView();

        return layout;
    }

    public void InitView (View view) {
        imageButton_door_open = view.findViewById(R.id.imageButton_door_open);
        imageButton_door_close = view.findViewById(R.id.imageButton_door_close);
    }

    public void AboutView () {
        imageButton_door_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlDoor("1");
            }
        });

        imageButton_door_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlDoor("0");
            }
        });
    }

    public void ControlDoor (final String onoff) {
        ServerInterface serverInterface = new Repo().getService();
        Call<Door_model> c = serverInterface.DoorOpen(onoff);
        c.enqueue(new Callback<Door_model>() {
            @Override
            public void onResponse(Call<Door_model> call, Response<Door_model> response) {
                String message = "";
                if (response.body().result.equals("1")) {
                   switch (onoff) {
                       case "1" :
                           message = "문이 열립니다.";
                           break;
                       case "0" :
                           message = "문이 닫힙니다.";
                           break;
                       default:;
                   }
                   Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Door_model> call, Throwable t) {
                Toast.makeText(context, "서버에 약간의 오류가 있습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
