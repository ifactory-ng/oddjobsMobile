package com.ifactory.oddjobs;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;

/**
 * Created by smilecs on 3/19/15.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
   private static final String AUTHORITY = "com.ifactory.Oddjobs";
    private static final String PREFIX = "content://" + AUTHORITY + "/";
    private static final String TAG = "Sync";

   //ContentResolver mResolver;
    public SyncAdapter(Context context, Boolean init){
        super(context, init);
     //   mResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.i(TAG, "Starting Sync");
        SyncResult result = new SyncResult();
        try{
            DeleteFeeds(provider);
            GetFeeds(provider);
}
        catch(RemoteException e){
            syncResult.hasHardError();
            Log.e(TAG, e.getMessage());
        }catch(IOException e){
            syncResult.hasHardError();
            Log.e(TAG, e.getMessage());
        }
        catch (JSONException e){
            syncResult.hasHardError();
            Log.e(TAG, e.getMessage());
        }
    }
    private void DeleteFeeds(ContentProviderClient client)throws RemoteException{
        client.delete(Uri.parse(PREFIX + "feeds"), null, null);

    }
    private void GetFeeds(ContentProviderClient client)throws RemoteException, IOException, JSONException{
        ContentValues values = new ContentValues();
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://10.0.2.2:8080/api/feeds");
        HttpResponse response = httpclient.execute(get);
        String result = EntityUtils.toString(response.getEntity());

        JSONArray js = new JSONArray(result);
        for(int i=0; i<js.length(); i++){
            JSONObject jss = js.getJSONObject(i);
            values.put(DataContract.feeds.SKILLNAME, jss.get("skillname").toString());
            values.put(DataContract.feeds.ADDRESS, jss.get("address").toString());
            values.put(DataContract.feeds.DESCRIPTION_COL, jss.get("description").toString());
            values.put(DataContract.feeds.LOCATION, jss.get("location").toString());
            values.put(DataContract.feeds.NAME, jss.get("username").toString());
            values.put(DataContract.feeds.SKILL_ID, jss.get("_id").toString());
            values.put(DataContract.feeds.RATE_COL, jss.get("rating").toString());
            client.insert(Uri.parse(PREFIX +"feeds"), values);

        }

    }
}
