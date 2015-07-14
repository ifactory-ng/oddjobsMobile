package com.ifactory.oddjobs;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.facebook.Session;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;


public class fb_login extends Activity{
    static final String APP_ID = "747991285264118";
    Button login_btn;
    Facebook fb = new Facebook(APP_ID);
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    //Context c;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = getSharedPreferences(getString(R.string.preference_file_name), MODE_PRIVATE);
        editor = sharedPref.edit();

        setContentView(R.layout.activity_fb_login);
        login_btn = (Button) findViewById(R.id.auth);
        login_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbAuth(v);
            }
        });


    }

    public void fbAuth(final View v){

        fb.authorize(this, new String[]{"publish_stream", "email"}, new Facebook.DialogListener() {
            @Override
            public void onComplete(Bundle values) {
                AsyncFacebookRunner mAsyn = new AsyncFacebookRunner(fb);
                mAsyn.request("me", new AsyncFacebookRunner.RequestListener() {

                    @Override
                    public void onComplete(String response, Object state) {
                        Log.d("profile", response);
                        String json = response;
                        HttpPost httpPost;
                        HttpClient httpclient;
                        try {
                            JSONObject profile = new JSONObject(json);
                            String name = profile.getString("name");
                            Log.d("name", name);
                            String id = profile.getString("id");
                            String email = profile.getString("email");
                            List<NameValuePair> value = new ArrayList<NameValuePair>();
                            value.add(new BasicNameValuePair("name", name));
                            value.add(new BasicNameValuePair("ID", id));
                            value.add(new BasicNameValuePair("email", email));
                            value.add(new BasicNameValuePair("provider", "facebook"));
                            Log.d("json ", id);
                            editor.putString("email", email);
                            editor.putString("id", id);
                            editor.putString("name", name);

                            httpclient = new DefaultHttpClient();
                            httpPost = new HttpPost(routes.AUTHENTICATE);

                            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
                            httpPost.setHeader("Accept-Language", "en-US");
                            httpPost.setEntity(new UrlEncodedFormEntity(value));
                          HttpResponse res = httpclient.execute(httpPost);

                            String result = EntityUtils.toString(res.getEntity());
                            Log.d("what", result);
                              JSONObject results = new JSONObject(result);

                            Log.d("result", result);
                             editor.putString("_id", results.getString("Im"));
                            editor.putString("l", result);

                            editor.apply();
                            Log.d("id", sharedPref.getString("id", "id"));
                            Log.d("json response", result);

                        } catch (ClientProtocolException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
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



    @Override
    public void onResume() {
        super.onResume();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
        Intent i = new Intent(getApplicationContext(), profile.class);
        startActivity(i);
    }
}