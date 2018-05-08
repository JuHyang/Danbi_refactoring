package com.seed.android.danbi;

/**
 * Created by kkss2 on 2018-05-08.
 */

public class Weather_CurrentData {
    Main main;

    public Main getMain() {
        return main;
    }

    public class Main {
        Double tepm;
        Integer pressure;
        Integer humidity;
        Double temp_min;
        Double temp_max;
    }
}
