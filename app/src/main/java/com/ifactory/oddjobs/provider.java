package com.ifactory.oddjobs;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by smilecs on 3/17/15.
 */
public class provider extends ContentProvider {
    private static final UriMatcher urimatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int POPULAR_FEEDS = 1;
private static final String AUTHORITY = "com.ifactory.oddjobs";
    static {
        urimatcher.addURI(AUTHORITY, "feeds", POPULAR_FEEDS);
    }
    private dbHelper dbms;
    @Override
    public boolean onCreate() {
        dbms = new dbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbms.getWritableDatabase();
        if (TextUtils.isEmpty(sortOrder)) sortOrder = "_ID ASC";


        switch(urimatcher.match(uri)){
            case POPULAR_FEEDS:

                return db.query(DataContract.feeds.TABLE_NAME, projection, selection, selectionArgs, null, null,sortOrder);
            default:
                throw new RuntimeException("No content provider URI match.");
        }
    }

    @Override
    public String getType(Uri uri) {
        switch (urimatcher.match(uri)){
            case POPULAR_FEEDS:
                return "vnd.android.cursor.dir/vnd.com.ifactory.oddjobs.feeds";
            default:
                throw new RuntimeException("No content provider URI match.");
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbms.getWritableDatabase();
        switch(urimatcher.match(uri)){
            case POPULAR_FEEDS:
                db.insertOrThrow(DataContract.feeds.TABLE_NAME, null, values);
                return Uri.parse("content://" + AUTHORITY + "/feeds");
            default:
                throw new RuntimeException("No content provider URI match.");
        }


    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbms.getWritableDatabase();
        switch(urimatcher.match(uri)){
            case POPULAR_FEEDS:
                return db.delete(DataContract.feeds.TABLE_NAME,null,null);
            default:
                throw new RuntimeException("No content provider URI match.");
        }

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
