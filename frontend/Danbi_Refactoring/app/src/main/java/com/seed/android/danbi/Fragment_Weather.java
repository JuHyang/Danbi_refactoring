package com.seed.android.danbi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kkss2 on 2018-04-04.
 */

@SuppressLint("ValidFragment")

public class Fragment_Weather extends Fragment {
    String appid = "3547a6ef5779bc93134f4a4d3d5bd995";
    Context context;

    public Fragment_Weather (Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View layout = inflater.inflate(R.layout.fragment_weather, container, false);

        return layout;
    }
}
