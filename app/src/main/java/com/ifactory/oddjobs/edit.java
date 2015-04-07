package com.ifactory.oddjobs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gc.materialdesign.views.ButtonFloat;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
                locations = location.getText().toString();
                abouts = about.getText().toString();
                addresss = address.getText().toString();
                phones = phone.getText().toString();
       List<NameValuePair> value = new ArrayList<NameValuePair>();
              
                    value.add(new BasicNameValuePair("location", locations));
                    value.add(new BasicNameValuePair("about", abouts));
                    value.add(new BasicNameValuePair("address", addresss));
                    value.add(new BasicNameValuePair("phone", phones));
              
                Context c = getActivity();
               SharedPreferences sharedPref = c.getSharedPreferences(c.getString(R.string.preference_file_name), c.MODE_PRIVATE);
                SharedPreferences.Editor editor;
                editor = sharedPref.edit();
                String id = sharedPref.getString("_id", "id");
            FutureTask<String> Jarray = new FutureTask<String>(new PostData(value, routes.EDIT_PROFILE + id));
                ExecutorService es = Executors.newSingleThreadExecutor();
                es.submit(Jarray);
                es.shutdown();
                editor.putString("about", abouts);
                editor.putString("address", addresss);
                editor.putString("phone", phones);
                editor.putString("location", locations);
                editor.commit();

                details details = new details();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                fragmentManager.beginTransaction().replace(R.id.mainContent, details).commit();



            }
        });



        return v;
        }

}