package com.ifactory.oddjobs;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by smilecs on 3/19/15.
 */
public class SyncService extends Service{
    private static SyncAdapter syncAdapter = null;
    private static final Object sSyncAdapterLock = new Object();

    @Override
    public void onCreate() {
        super.onCreate();
        synchronized (sSyncAdapterLock){
            if(syncAdapter == null){
                syncAdapter = new SyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return syncAdapter.getSyncAdapterBinder();
    }
}

