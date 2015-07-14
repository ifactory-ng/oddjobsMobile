package com.ifactory.oddjobs;

import android.app.Application;
import android.content.Context;

/**
 * Created by smilecs on 6/5/15.
 */
public class ApplicationClass extends Application {
    private static ApplicationClass sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
    public static ApplicationClass getsInstance(){
        return sInstance;
    }
    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}
