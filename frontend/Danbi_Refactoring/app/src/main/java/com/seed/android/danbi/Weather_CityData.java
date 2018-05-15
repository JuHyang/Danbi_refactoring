package com.seed.android.danbi;

import com.orm.SugarRecord;

/**
 * Created by kkss2 on 2018-05-09.
 */

public class Weather_CityData extends SugarRecord {
    double lat;
    double lon;

    private Weather_GpsInfo gps;

    public Weather_CityData () {}

    public Weather_CityData (double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
