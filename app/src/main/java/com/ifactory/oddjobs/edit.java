package com.ifactory.oddjobs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gc.materialdesign.views.ButtonFloat;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by smile on 12/9/14.
 */
public class edit extends Fragment {
    Button save;
    String locations, abouts, addresss, phones;
    EditText phone, address, about, location;
    Context c;
    SharedPreferences sharedpref;
    SharedPreferences.Editor editor;
    String id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c = getActivity();
//        c =g
        sharedpref = c.getSharedPreferences(c.getString(R.string.preference_file_name), c.MODE_PRIVATE);
        editor = sharedpref.edit();
        id = sharedpref.getString("_id", "id");


    }
    @Override
        public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup
        container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit, container, false);

        location = (EditText) v.findViewById(R.id.location);

        about = (EditText) v.findViewById(R.id.editText2);

        address = (EditText) v.findViewById(R.id.address);
        phone = (EditText) v.findViewById(R.id.phone);

        save = (Button) v.findViewById(R.id.button);
        save.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {



            /*FutureTask<String> Jarray = new FutureTask<String>(new PostData(value, routes.EDIT_PROFILE + id));
                ExecutorService es = Executors.newSingleThreadExecutor();
                es.submit(Jarray);
                es.shutdown();
              */
                //Log.d("data", value.toString());
                new profileedit().execute();

                details details = new details();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                fragmentManager.beginTransaction().replace(R.id.mainContent, details).commit();



            }
        });



        return v;
        }

private class profileedit extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... params) {
        String result = " ";
         c = getActivity();
        locations = location.getText().toString();
        abouts = about.getText().toString();
        addresss = address.getText().toString();
        phones = phone.getText().toString();
        try {
            List<NameValuePair> value = new ArrayList<NameValuePair>(4);

            //Log.d("u", rs);
              value.add(new BasicNameValuePair("location", locations));
            value.add(new BasicNameValuePair("about", abouts));
            value.add(new BasicNameValuePair("address", addresss));
            value.add(new BasicNameValuePair("phone", phones));
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost post = new HttpPost(routes.EDIT_PROFILE + id);

            post.setEntity(new UrlEncodedFormEntity(value));
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");
            post.setHeader("Accept-Language", "en-US");


            HttpResponse response = httpclient.execute(post);
            result = EntityUtils.toString(response.getEntity());

        }catch (IOException i){
            i.getMessage();
        }
return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        editor.putString("about", abouts);
        editor.putString("address", addresss);
        editor.putString("phone", phones);
        editor.putString("location", locations);
        editor.apply();
        editor.commit();

    }
}
}