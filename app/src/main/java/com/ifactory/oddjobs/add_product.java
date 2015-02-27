package com.ifactory.oddjobs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by smilecs on 1/16/15.
 */
public class add_product extends Fragment {

EditText add, locale, tag, desc, p_name;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_product,container, false);

        add = (EditText) v.findViewById(R.id.add);
        locale = (EditText) v.findViewById(R.id.loc);
        tag = (EditText) v.findViewById(R.id.tag_name);
        desc = (EditText) v.findViewById(R.id.desc);
        p_name = (EditText) v.findViewById(R.id.product_name);
        Button submit = (Button) v.findViewById(R.id.submit);
        submit.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                JSONObject value = new JSONObject();
                try {
                    value.put("p_name", p_name.getText().toString());
                    value.put("desc", desc.getText().toString());
                    value.put("address", add.getText().toString());
                    value.put("location", locale.getText().toString());
                    value.put("tag", tag.getText().toString());
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                Context c = getActivity();
                SharedPreferences editor = c.getSharedPreferences(c.getString(R.string.preference_file_name), c.MODE_PRIVATE);
                String id = editor.getString("id", "id");
                Log.d("test", id);
                FutureTask<JSONObject> Jarray = new FutureTask<JSONObject>(new PostData(value, routes.ADD_PRODUCT + id));
                ExecutorService es = Executors.newSingleThreadExecutor();
                es.submit(Jarray);
                es.shutdown();


                details details = new details();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                fragmentManager.beginTransaction().replace(R.id.mainContent, details).commit();
            }

            });

        return  v;

    }
}
