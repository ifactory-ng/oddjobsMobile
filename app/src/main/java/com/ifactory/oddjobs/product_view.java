package com.ifactory.oddjobs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by smilecs on 1/22/15.
 */
public class product_view extends Fragment {
TextView desc, location, address, name;
    RatingBar rt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       super.onCreateView(inflater, container, savedInstanceState);
        rt = (RatingBar) getActivity().findViewById(R.id.ratingBar);
        //rt.isIndicator();

        String data = getArguments().getString("data");
        Log.d("test", data);
        new loadDetails().execute(data);
            return inflater.inflate(R.layout.user_product, container, false);
    }
    private class loadDetails extends AsyncTask<String, String, String>{


        @Override
        protected String doInBackground(String... params) {
            String result= "";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet get = new HttpGet(routes.GET_SKILL + params[0]);

                HttpResponse response = httpclient.execute(get);
                result = EntityUtils.toString(response.getEntity());
                Log.d("result", result);
            }
            catch(IOException i){
                i.printStackTrace();
            }
            //JSONArray rs = new JSONArray(result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject Jobject = new JSONObject(s);
                desc = (TextView) getActivity().findViewById(R.id.user_product_desc);
                desc.setText(Jobject.get("Description").toString());
                location = (TextView) getActivity().findViewById(R.id.user_product_location);
                location.setText(Jobject.get("Location").toString());
                address = (TextView) getActivity().findViewById(R.id.user_product_address);
                address.setText(Jobject.get("Address").toString());
                name = (TextView) getActivity().findViewById(R.id.user_product_name);
                name.setText(Jobject.get("SkillName").toString());
     //         int rate = Integer.parseInt(Jobject.get("Rating").toString());
       //         rt.setNumStars(rate);
            }
            catch (JSONException j){
                j.printStackTrace();
            }
        }
    }
}
