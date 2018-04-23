package com.seed.android.danbi;

import com.orm.SugarRecord;

/**
 * Created by kkss2 on 2018-04-04.
 */

public class Water_AlarmData extends SugarRecord {
    int hour;
    int minute;
    int during;
    boolean onoff;
    boolean repeat;

    public Water_AlarmData() {}

    public Water_AlarmData(int hour, int minute, int during, boolean onoff, boolean repeat) {
        this.hour = hour;
        this.minute = minute;
        this.during = during;
        this.onoff = onoff;
        this.repeat = repeat;
    }
    public int getHour() {
        return hour;
    }
    public int getMinute() {
        return minute;
    }
    public int getDuring() {
        return during;
    }
    public void setHour(int hour) {
        this.hour = hour;
    }
    public void setMinute(int minute) {
        this.minute = minute;
    }
    public void setDuring(int during) {
        this.during = during;
    }

}
