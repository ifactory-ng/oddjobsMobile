package com.ifactory.oddjobs;

import android.provider.BaseColumns;

/**
 * Created by smilecs on 3/17/15.
 */
public final class DataContract {
    private DataContract(){}
    public static abstract class feeds implements BaseColumns{
        public static final String TABLE_NAME = "feeds";
    }
}
