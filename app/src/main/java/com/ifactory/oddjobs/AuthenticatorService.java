package com.ifactory.oddjobs;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by smilecs on 3/18/15.
 */
public class AuthenticatorService extends Service {
    private Authenticator mAuthenticator;

    @Override
    public void onCreate() {
        mAuthenticator = new Authenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
