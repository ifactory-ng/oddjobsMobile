package com.ifactory.oddjobs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<NameValuePair> value = new ArrayList<NameValuePair>();
                        value.add(new BasicNameValuePair("p_name", p_name.getText().toString()));
                        value.add(new BasicNameValuePair("desc", desc.getText().toString()));
                        value.add(new BasicNameValuePair("address", add.getText().toString()));
                        value.add(new BasicNameValuePair("location", locale.getText().toString()));
                        value.add(new BasicNameValuePair("tag", tag.getText().toString()));

                        Context c = getActivity();
                        SharedPreferences editor = c.getSharedPreferences(c.getString(R.string.preference_file_name), c.MODE_PRIVATE);
                        String id = editor.getString(c.getString(R.string.preference_file_name), "id");
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost(routes.ADD_PRODUCT + id);
                        try {
                            httpPost.setEntity(new UrlEncodedFormEntity(value));
                            HttpResponse res = httpclient.execute(httpPost);
                            String result = EntityUtils.toString(res.getEntity());
                            Log.d("result", result);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (ClientProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });
        return  v;

    }
}
