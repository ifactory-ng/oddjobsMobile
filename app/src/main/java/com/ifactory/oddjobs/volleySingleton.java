package com.ifactory.oddjobs;

import android.app.DownloadManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by smilecs on 6/3/15.
 */
public class volleySingleton {
    private static volleySingleton sInstance = null;
    private RequestQueue mRequestQueue;
    private volleySingleton(){
        mRequestQueue = Volley.newRequestQueue(ApplicationClass.getAppContext());

    }
    public static volleySingleton getsInstance(){
        if(sInstance == null){
            sInstance = new volleySingleton();
        }
        return sInstance;
    }
    public RequestQueue getmRequestQueue(){
        return mRequestQueue;
    }
}
