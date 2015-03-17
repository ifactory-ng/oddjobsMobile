package com.ifactory.oddjobs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by smilecs on 3/16/15.
 */
public class dbHelper extends SQLiteOpenHelper{
   private static final String DATABASE_NAME = "oddjobs";
   private static final String DATABASE_TABLE = "feeds";
    private static final int VERSION = 1;
    private static final String UID = "_id";
    private static final String RATE = "rate";
    private static final String DESC = "desc";
    private static final String NAME = "name";
    private static final String LOCATION = "location";
    private static final String ADDRESS = "address";
    private static final String  CREATE_TABLE = "CREATE TABLE "+ DATABASE_TABLE+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+DESC+" VARCHAR(255), "+LOCATION+" VARCHAR(255), "+RATE+" VARCHAR(255), "+NAME+" VARCHAR(255), "+ADDRESS+" VARCHAR(255)";
    public dbHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
