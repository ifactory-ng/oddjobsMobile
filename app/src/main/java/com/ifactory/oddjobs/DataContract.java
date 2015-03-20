package com.ifactory.oddjobs;

import android.provider.BaseColumns;

/**
 * Created by smilecs on 3/17/15.
 */
public final class DataContract {
    private DataContract(){}
    public static abstract class feeds implements BaseColumns{
        public static final String TABLE_NAME = "feeds";
        public static final String DESCRIPTION_COL = "desc";
        public static final String RATE_COL = "rate";
        public static final String ADDRESS = "address";
        public static final String LOCATION = "location";
        public static final String NAME = "name";
        public static final String SKILLNAME = "skill_name";
        public static final String SKILL_ID = "skill_id";
    }
}
