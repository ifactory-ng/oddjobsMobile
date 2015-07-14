package com.ifactory.oddjobs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonFloat;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by smilecs on 2/18/15.
 */
public class product_result extends Activity {
    ImageView productView;
    TextView product_title, product_desc, product_location, product_address, product_phone, username;
    Context c;
    String id;
    static final String APP_ID = "747991285264118";
    Facebook fb;
    String ids;
    TextView rt3;
  //  ButtonFlat review;
    SharedPreferences.Editor editor;
    //ButtonFloat bookmark;
    Bitmap pic;
    SharedPreferences sharedpref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fb = new Facebook(APP_ID);
        sharedpref = getSharedPreferences(getString(R.string.preference_file_name), MODE_PRIVATE);
        editor = sharedpref.edit();
        ids = sharedpref.getString("_id", "id");
        super.onCreate(savedInstanceState);
        final Context context = getApplicationContext();
        setContentView(R.layout.product_result);
        product_title = (TextView)findViewById(R.id.name);
        product_desc = (TextView)findViewById(R.id.abouttxt);
        product_location = (TextView)findViewById(R.id.location);
        product_address = (TextView)findViewById(R.id.address);
        product_phone =(TextView)findViewById(R.id.phone);
      //  username = (TextView)findViewById(R.id.skillowner);
        productView = (ImageView)findViewById(R.id.propic);
        //rt3 = (TextView) findViewById(R.id.ratingBar3);
        /*review = (ButtonFlat) findViewById(R.id.bookmarksubmit);
        review.setOnClickListener(new ButtonFlat.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(ids == "id"){
                    fbAuth(v);

                                }else {
                    Intent i = new Intent(getApplicationContext(), review.class);
                    startActivity(i);
                }
            }
        });
        bookmark = (ButtonFloat) findViewById(R.id.bookmarkbutton);
        bookmark.setOnClickListener(new ButtonFloat.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ids == "id"){
                    Toast.makeText(context, "please login to use this feature", Toast.LENGTH_SHORT).show();

                }else {
                    List<NameValuePair> value = new ArrayList<NameValuePair>(4);
                    value.add(new BasicNameValuePair("Name", username.getText().toString()));
                    value.add(new BasicNameValuePair("SkillName", product_title.getText().toString()));
                    value.add(new BasicNameValuePair("Id", id));
                    value.add(new BasicNameValuePair("Phone", product_phone.getText().toString()));

//                String id ="963176113698271";
                    Log.d("test", id);
                    FutureTask<String> Jarray = new FutureTask<String>(new PostData(value, routes.BOOKMARK + ids));
                    ExecutorService es = Executors.newSingleThreadExecutor();
                    es.submit(Jarray);
                    es.shutdown();
                }
            }
        });
        new GetData().execute();

*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product_result, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if(item.getItemId() == R.id.action_search){
            Intent i = new Intent(getApplicationContext(), searchDialog.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
    private class GetData extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            String result = " ";
           try {
               HttpClient httpclient = new DefaultHttpClient();
               HttpGet get = new HttpGet(routes.GET_SKILL + getIntent().getStringExtra("id"));
               HttpResponse response = httpclient.execute(get);
               result = EntityUtils.toString(response.getEntity());
               //calls(id);
           }
           catch (IOException i){
           i.getMessage();
           }

            return result;
        }
        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            try {
                JSONObject jo = new JSONObject(s);
                id = jo.getString("Id");
                product_title.setText(jo.getString("SkillName"));
                product_phone.setText(jo.getString("phone"));
                product_address.setText(jo.getString("Address"));
                product_location.setText(jo.getString("Location"));
                product_desc.setText(jo.getString("Description"));
                username.setText(jo.getString("UserName"));
            rt3.setText(jo.getString("Rating"));
               pic = Fbpic.profile_pic(id);
                productView.setImageBitmap(pic);

            }
            catch(JSONException j ){
                j.getMessage();
            }
        }
    }
    public void fbAuth(final View v) {
        fb.authorize(this, new String[]{"publish_stream", "email"}, new Facebook.DialogListener() {

            @Override
            public void onFacebookError(FacebookError e) {

            }

            @Override
            public void onError(DialogError e) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onComplete(Bundle values) {
                AsyncFacebookRunner mAsyn = new AsyncFacebookRunner(fb);
                mAsyn.request("me", new AsyncFacebookRunner.RequestListener() {

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
                /*add facebook values to sharepreferences for application level access
                 */
                            editor.putString("email", email);
                            editor.putString("id", id);
                            editor.putString("name", name);

                            httpclient = new DefaultHttpClient();
                            httpPost = new HttpPost(routes.AUTHENTICATE);

                            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
                            httpPost.setHeader("Accept-Language", "en-US");
                            httpPost.setEntity(new UrlEncodedFormEntity(value));
                            // DataLoader dl = new DataLoader();
                            HttpResponse res = httpclient.execute(httpPost);

                            String result = EntityUtils.toString(res.getEntity());
                            Log.d("what", result);
                            JSONObject results = new JSONObject(result);

                            Log.d("result", result);


                            //String _id = newId.getString("userid");
                            editor.putString("_id", results.getString("Im"));
                            editor.putString("l", result);

                            editor.apply();
                            editor.commit();
                            Log.d("id", sharedpref.getString("id", "id"));
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

                });
            }
        });
    }
}