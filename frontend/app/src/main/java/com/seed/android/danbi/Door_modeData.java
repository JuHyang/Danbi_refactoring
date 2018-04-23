package com.seed.android.danbi;

import com.orm.SugarRecord;

/**
 * Created by kkss2 on 2018-04-04.
 */

public class Door_modeData extends SugarRecord {
    boolean mode;


    public Door_modeData() {}


    public Door_modeData(Boolean mode) {
        this.mode = mode;
    }
}
