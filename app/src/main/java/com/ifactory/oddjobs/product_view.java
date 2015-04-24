package com.ifactory.oddjobs;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.gc.materialdesign.views.ButtonFloat;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smilecs on 1/22/15.
 */
public class product_view extends Fragment {
TextView desc, location, address, pname, name, phone;
    ButtonFloat bookmark;
    TextView rt;
    SharedPreferences shared;
    String userid;
    String id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context c = getActivity();
        shared = c.getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE);
        userid = shared.getString("_id", "id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.user_product, container, false);
        rt = (TextView) v.findViewById(R.id.ratingBar);
        desc = (TextView) v.findViewById(R.id.user_product_desc);
        location = (TextView) v.findViewById(R.id.user_product_location);
        address = (TextView) v.findViewById(R.id.user_product_address);
        pname = (TextView) v.findViewById(R.id.user_product_name);
       // name = (TextView) v.findViewById(R.id.skillowner);
        phone = (TextView) v.findViewById(R.id.user_product_phone);
        //rt.isIndicator();

        String data = getArguments().getString("data");
        Log.d("test", data);
        new loadDetails().execute(data);
            return v;
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
/*        Id          bson.ObjectId `json:"_id,omitempty" bson:"_id,omitempty"`
        Slug        string
        SkillName   string    `json:"SkillName"`
        UserName    string    `json:"UserName"`
        Tags        []string  `json:"Tags"`
        Phone       string    `json:"Phone"`
        UserID      string `json:"UserID"`
        Location    string    `json:"Location"`
        Address     string  `json:"Address"`
        Price       string  `json:"Price"`
        Description string  `json:"Description"`
        Comments    []Comment
        Rating      int
*/
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject Jobject = new JSONObject(s);
                desc.setText(Jobject.getString("Description"));
                location.setText(Jobject.getString("Location"));
                address.setText(Jobject.getString("Address"));
//                name.setText(Jobject.getString("UserName"));
                pname.setText(Jobject.getString("SkillName"));
                id = Jobject.getString("_id");
                phone.setText(Jobject.getString("Phone"));
               rt.setText(Jobject.getString("Rating"));
            //    rt.setNumStars(rate);
            }
            catch (JSONException j){
                j.printStackTrace();
            }catch (NullPointerException npe){
                npe.getMessage();
            }
        }
    }
}
