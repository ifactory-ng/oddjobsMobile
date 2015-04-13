package com.ifactory.oddjobs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by smilecs on 2/18/15.
 */
public class product_result extends Activity {
    ImageView productView;
    TextView product_title, product_desc, product_location, product_address, product_phone, username;
    Button review, bookmark;
    Context c;
    String id;
    RatingBar rt3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_result);
        product_title = (TextView)findViewById(R.id.product_title);
        product_desc = (TextView)findViewById(R.id.product_about);
        product_location = (TextView)findViewById(R.id.product_locale);
        product_address = (TextView)findViewById(R.id.product_address);
        product_phone =(TextView)findViewById(R.id.product_number);
        username = (TextView)findViewById(R.id.skillowner);
        productView = (ImageView)findViewById(R.id.imageView1);
        rt3 = (RatingBar) findViewById(R.id.ratingBar3);
        new GetData().execute();

        

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
           }
           catch (IOException i){
           i.getMessage();
           }

            return result;
        }
        /*Id          bson.ObjectId `json:"_id" bson:"_id,omitempty"`
        SkillName   string
        UserName    string
        Tags        []string
        Phone       string
        UserID      string
        Location    string
        Address     string
        Price       string
        Description string
        Comments    []Comment
        Rating      int
*/
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
                rt3.setNumStars(Integer.parseInt(jo.getString("Rating")));

            }
            catch(JSONException j ){
                j.getMessage();
            }
        }
    }


}
