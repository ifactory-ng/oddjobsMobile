package com.ifactory.oddjobs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


public class details extends android.support.v4.app.Fragment{


    //private OnFragmentInteractionListener mListener;
    TextView user_email, user_name, user_about, user_phone, user_address, user_location;
    Context c;
    String email;
    String name;
    String location;
    String phone;
    String about;
    String address;
    JSONObject jObj;
    String result;
    ImageView pics;
    String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         c = getActivity();
        SharedPreferences editor = c.getSharedPreferences(c.getString(R.string.preference_file_name), c.MODE_PRIVATE);
        id = editor.getString("id", "id");

        new load_details(c).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.details, container, false);
FutureTask<Bitmap> future = new FutureTask<Bitmap>(new Callable<Bitmap>() {
    @Override
    public Bitmap call() throws Exception {
        Bitmap pic = getPhotoFacebook(id);
        return pic;
    }

}); try {
            Bitmap pic = future.get();
            pics.setImageBitmap(pic);
        }
        catch (InterruptedException i){
            i.printStackTrace();
        }
        catch (ExecutionException e){
            e.printStackTrace();
        }
            user_email = (TextView) v.findViewById(R.id.email);
       user_name = (TextView) v.findViewById(R.id.name);
        user_about = (TextView)v.findViewById(R.id.about);

        user_location = (TextView) v.findViewById(R.id.location);

        user_address = (TextView) v.findViewById(R.id.address);

        user_phone = (TextView) v.findViewById(R.id.phone);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       }

    @Override
    public void onDetach() {
        super.onDetach();
    //    mListener = null;
    }




    private class load_details extends AsyncTask<String, Void, String>{
        Bitmap pic;
        Context c;
        public load_details(Context c){
            this.c = c;

        }
        @Override
        protected String doInBackground(String... params) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(routes.PROFILE + id);
            try {
                HttpResponse res = httpclient.execute(httpGet);
                result = EntityUtils.toString(res.getEntity());
                getPhotoFacebook(id);
            }

            catch (IOException i){
                i.printStackTrace();
            }


            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                //JSONArray rs = new JSONArray(s);
                jObj = new JSONObject(s);
                email = jObj.getString("email");
                name = jObj.getString("name");
                //location = jObj.getString("location");
                //  phone = jObj.getString("phone");
                //about = jObj.getString("about");
                //address = jObj.getString("address");
                user_email.setText(email);
                user_name.setText(name);
                ///user_about.setText(about);
                //user_location.setText(location);
                //user_address.setText(address);
                //user_phone.setText(phone);
                //pics.setImageBitmap(pic);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap getPhotoFacebook(final String id) {

        Bitmap bitmap=null;
        final String nomimg = "https://graph.facebook.com/"+id+"/picture?type=large";
        URL imageURL = null;

        try {
            imageURL = new URL(nomimg);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection connection = (HttpURLConnection) imageURL.openConnection();
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects( true );
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            //img_value.openConnection().setInstanceFollowRedirects(true).getInputStream()
            bitmap = BitmapFactory.decodeStream(inputStream);

        } catch (IOException e) {

            e.printStackTrace();
        }
        return bitmap;

    }
}