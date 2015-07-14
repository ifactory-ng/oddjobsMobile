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


import com.gc.materialdesign.views.ButtonFlat;

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

    EditText edit_fone, add, locale, tag, desc, p_name;
   Button submit;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_product, container, false);

        add = (EditText) v.findViewById(R.id.add);
        locale = (EditText) v.findViewById(R.id.loc);
        tag = (EditText) v.findViewById(R.id.tag_name);
        desc = (EditText) v.findViewById(R.id.desc);
        edit_fone = (EditText) v.findViewById(R.id.edit_phone);
        p_name = (EditText) v.findViewById(R.id.product_name);
        submit = (Button) v.findViewById(R.id.save);
        submit.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                List<NameValuePair> lt = new ArrayList<NameValuePair>(6);

                lt.add(new BasicNameValuePair("skill_name", p_name.getText().toString()));
                lt.add(new BasicNameValuePair("desc", desc.getText().toString()));
                lt.add(new BasicNameValuePair("address", add.getText().toString()));
                lt.add(new BasicNameValuePair("tag", tag.getText().toString()));
                lt.add(new BasicNameValuePair("phone", edit_fone.getText().toString()));
                lt.add(new BasicNameValuePair("location", locale.getText().toString()));
                Context c = getActivity();
                SharedPreferences editor = c.getSharedPreferences(c.getString(R.string.preference_file_name), c.MODE_PRIVATE);
            String id = editor.getString("_id", "id");
    //            String id ="963176113698271";
                Log.d("test", id);
                FutureTask<String> Jarray = new FutureTask<String>(new PostData(lt, routes.ADD_SKILL + id));
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