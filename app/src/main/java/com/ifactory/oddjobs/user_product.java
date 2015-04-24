package com.ifactory.oddjobs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
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
public class user_product extends Fragment {

    TextView location, address, description, name, phone;
    RatingBar ratingBar;

    SharedPreferences editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ratingBar = (RatingBar) getActivity().findViewById(R.id.ratingBar);
        location = (TextView) getActivity().findViewById(R.id.user_product_location);
        phone = (TextView) getActivity().findViewById(R.id.user_product_phone);
        address = (TextView) getActivity().findViewById(R.id.user_product_address);
        name = (TextView) getActivity().findViewById(R.id.user_product_name);
        description = (TextView) getActivity().findViewById(R.id.user_product_desc);
        new GetUserSkill().execute();
        return inflater.inflate(R.layout.user_product, container, false);

    }

    private class GetUserSkill extends AsyncTask<String, Void, String>{
        Context c = getActivity();
        String result;
        String id;
        @Override
        protected String doInBackground(String... params) {
            editor = c.getSharedPreferences(c.getString(R.string.preference_file_name), c.MODE_PRIVATE);
            id = editor.getString("_id", "id");
            try {

                HttpClient httpclient = new DefaultHttpClient();
                HttpGet get = new HttpGet(routes.BOOKMARK + id);
                HttpResponse response = httpclient.execute(get);
                result = EntityUtils.toString(response.getEntity());
            }
            catch (IOException i){
                i.getMessage();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
            JSONObject data = new JSONObject(s);
                description.setText(data.getString("Description"));
                name.setText(data.getString("SkillName"));
                phone.setText(data.getString("Phone"));
                address.setText(data.getString("Address"));
                location.setText(data.getString("Location"));
                ratingBar.setNumStars(data.getInt("Rating"));
            }catch (JSONException e){
                e.getMessage();
            }
        }
    }

}
