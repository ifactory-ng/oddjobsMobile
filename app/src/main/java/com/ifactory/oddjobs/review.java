package com.ifactory.oddjobs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class review extends Activity {
Button reviewbutton;
    EditText reviewtext;
    RatingBar  rb;
    String id, name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviews);
        SharedPreferences editor = getSharedPreferences(getString(R.string.preference_file_name), MODE_PRIVATE);
         id = editor.getString("_id", "id");
        name = editor.getString("name", "name");
        reviewbutton =(Button) findViewById(R.id.reviewbutton);
        reviewtext = (EditText) findViewById(R.id.review);
        rb = (RatingBar) findViewById(R.id.ratingBar2);
        reviewbutton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                String rating = String.valueOf(rb.getRating());
                List<NameValuePair> lt = new ArrayList<NameValuePair>(3);
                lt.add(new BasicNameValuePair("Comment", reviewtext.getText().toString()));
                lt.add(new BasicNameValuePair("Rating", rating));
                lt.add(new BasicNameValuePair("Name", name));
                FutureTask<String> Jarray = new FutureTask<String>(new PostData(lt, routes.REVIEW + id));
                ExecutorService es = Executors.newSingleThreadExecutor();
                es.submit(Jarray);
                es.shutdown();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_review, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
