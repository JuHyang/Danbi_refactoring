package com.seed.android.danbi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by kkss2 on 2018-04-04.
 */

@SuppressLint("ValidFragment")

public class Fragment_Temperature extends Fragment {
    Context context;
    private ImageButton imageButton_refresh;
    private TextView textView_temp, textView_status_temp, textView_species_temp, textView_proper_temp;
    private TextView textView_hum, textView_status_hum, textView_species_hum, textView_proper_hum;

    public Fragment_Temperature (Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View layout = inflater.inflate(R.layout.fragment_temperature, container, false);

        InitModel();
        InitVIew(layout);
        AboutView();

        return layout;
    }

    public void InitModel() {

    }

    public void InitVIew(View view) {
        imageButton_refresh = view.findViewById(R.id.imageButton_refresh);

        textView_temp = view.findViewById(R.id.textView_temp);
        textView_status_temp = view.findViewById(R.id.textView_status_temp);
        textView_species_temp = view.findViewById(R.id.textView_species_temp);
        textView_proper_temp = view.findViewById(R.id.textView_proper_temp);

        textView_hum = view.findViewById(R.id.textView_hum);
        textView_status_hum = view.findViewById(R.id.textView_status_hum);
        textView_species_hum = view.findViewById(R.id.textView_species_hum);
        textView_proper_hum = view.findViewById(R.id.textView_proper_hum);

    }

    public void AboutView() {

    }
}
