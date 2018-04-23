package com.seed.android.danbi;

import com.orm.SugarRecord;

/**
 * Created by kkss2 on 2018-04-04.
 */

public class Temperature_SettingData extends SugarRecord {
    int temperature;
    int humidity;

    public Temperature_SettingData() {}

    public Temperature_SettingData(int temperature, int humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }
}
