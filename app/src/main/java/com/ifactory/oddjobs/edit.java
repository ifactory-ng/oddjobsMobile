package com.ifactory.oddjobs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * Created by smile on 12/9/14.
 */
public class edit extends Fragment {
    Button save;
    String names, locations, abouts, addresss, phones;
    EditText phone, address, about, location;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
        public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup
        container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.edit, container, false);

         location = (EditText) v.findViewById(R.id.location);

         about = (EditText) v.findViewById(R.id.editText2);

        address = (EditText) v.findViewById(R.id.address);
         phone = (EditText) v.findViewById(R.id.phone);

        save = (Button) v.findViewById(R.id.button);
        save.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        locations = location.getText().toString();
                        abouts = about.getText().toString();
                        addresss = address.getText().toString();
                        phones = phone.getText().toString();
                        List<NameValuePair> value = new ArrayList<NameValuePair>();
                        value.add(new BasicNameValuePair("location", locations));
                        value.add(new BasicNameValuePair("about", abouts));
                        value.add(new BasicNameValuePair("address", addresss));
                        value.add(new BasicNameValuePair("phone", phones));
                  /*      String filename = "myfile";
                        String string = names+";"+abouts+";"+locations+";"+addresss+";"+phones+";";


                        try {
                            FileOutputStream  outputStream = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
                            outputStream.write(string.getBytes());
                            outputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                /*add facebook values to sharepreferences for application level access
                 */
                        Context c = getActivity();
                        SharedPreferences editor = c.getSharedPreferences(c.getString(R.string.preference_file_name), c.MODE_PRIVATE);
                        String id = editor.getString(c.getString(R.string.preference_file_name), "id");
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost(routes.EDIT_PROFILE + id);
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


        return v;
        }

}