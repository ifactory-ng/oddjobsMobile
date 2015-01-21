package com.ifactory.oddjobs;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.*;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;


public class fb_login extends Activity {
    //private static final List<String> PERMISSIONS = Arrays.asList("email");
    String APP_ID = "747991285264118";
    Button login_btn;
    Facebook fb = new Facebook(APP_ID);
    String email;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    AsyncFacebookRunner mAsyn = new AsyncFacebookRunner(fb);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb_login);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.ifactory.oddjobs",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        //Log.d("access", fb.getAccessToken());
        sharedPref = getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE);
         editor = sharedPref.edit();

        login_btn = (Button) findViewById(R.id.auth);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbAuth();
            }
        });
     //   Intent i = new Intent(this, profile.class);
       // startActivity(i);
    }


public void fbAuth(){
String access_token = sharedPref.getString("access_token", null);
    long expires = sharedPref.getLong("access_expires", 0);
    if(access_token != null){
        fb.setAccessToken(access_token);
    }
    if(expires !=0){
        fb.setAccessExpires(expires);
    }
    if(!fb.isSessionValid()) {

        fb.authorize(this, new String[]{"publish_stream"}, new Facebook.DialogListener() {
            @Override
            public void onComplete(Bundle values) {
                Log.d("access2", fb.getAccessToken());
editor.putString("access_token", fb.getAccessToken());
                editor.putLong("access_expires", fb.getAccessExpires());
                editor.commit();

                mAsyn.request("me", new RequestListener(){

                    @Override
                    public void onComplete(String response, Object state) {
                        Log.d("profile", response);
                        String json = response;
                        HttpPost httpPost;
                        HttpClient httpclient;
                        try {
                            JSONObject profile = new JSONObject(json);
                            String name = profile.getString("name");
                            String id = profile.getString("id");
                            email = profile.getString("email");
                            List<NameValuePair> value = new ArrayList<NameValuePair>();
                            value.add(new BasicNameValuePair("name", name));
                            value.add(new BasicNameValuePair("id", id));
                            value.add(new BasicNameValuePair("email", email));
                /*add facebook values to sharepreferences for application level access
                 */
                            editor.putString("email", email);
                            editor.putString("id", id);
                            httpclient = new DefaultHttpClient();
                            httpPost = new HttpPost("http://oddjobs.herokuapps.com/profile/authenticate");
                            try {
                                httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
                                httpPost.setEntity(new UrlEncodedFormEntity(value));
                                HttpResponse res = httpclient.execute(httpPost);
                                String result = EntityUtils.toString(res.getEntity());
                                JSONArray rs = new JSONArray(result);
                                JSONObject rss = rs.getJSONObject(0);
                                Log.d("json response", result);
                                email = rss.getString("email");
                                final String app_id = rss.getString("authID");

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "accessing account at oddjobs" + email + app_id, Toast.LENGTH_SHORT);
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (ClientProtocolException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onIOException(IOException e, Object state) {

                    }

                    @Override
                    public void onFileNotFoundException(FileNotFoundException e, Object state) {

                    }

                    @Override
                    public void onMalformedURLException(MalformedURLException e, Object state) {

                    }

                    @Override
                    public void onFacebookError(FacebookError e, Object state) {

                    }
                });

            }

            @Override
            public void onFacebookError(FacebookError e) {

            }

            @Override
            public void onError(DialogError e) {

            }

            @Override
            public void onCancel() {

            }
        });
    }

}

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fb_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }
}
