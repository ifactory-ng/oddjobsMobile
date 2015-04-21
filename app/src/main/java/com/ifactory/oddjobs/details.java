package com.ifactory.oddjobs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.android.Facebook;
import com.gc.materialdesign.views.ButtonFloat;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    //JSONObject jObj;
    //String result;
    ImageView px;
    String id;
    Bitmap pic;
    //RoundImage rd;
    ButtonFloat edit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         c = getActivity();
        SharedPreferences editor = c.getSharedPreferences(c.getString(R.string.preference_file_name), c.MODE_PRIVATE);
//        id = editor.getString("_id", "id");
  //      String fbid = editor.getString("id", "id");
       address = editor.getString("address","please fill your profile");
        location = editor.getString("location", " ");
        phone = editor.getString("phone", "please fill your profile");
        about = editor.getString("about", "please fill your profile");
        name = editor.getString("name", " ");
        email = editor.getString("email", "please fill your profile");
            }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        String fbid ="963176113698271";

        FutureTask<Bitmap> task = new FutureTask<Bitmap>(new Facebook_pic(fbid));
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(task);
        try{
            pic = task.get();
        }
        catch (InterruptedException i){
            i.printStackTrace();
        }
        catch (ExecutionException e){
            e.printStackTrace();
        }
        es.shutdown();


        View v = inflater.inflate(R.layout.details, container, false);
           user_email = (TextView) v.findViewById(R.id.email);
       user_name = (TextView) v.findViewById(R.id.name);
        user_about = (TextView)v.findViewById(R.id.about);

        user_location = (TextView) v.findViewById(R.id.location);

        user_address = (TextView) v.findViewById(R.id.addr);

        user_phone = (TextView) v.findViewById(R.id.phone);
        px = (ImageView) v.findViewById(R.id.pro_pic);
        user_email.setText(email);
        user_name.setText(name);
        user_about.setText(about);
        user_location.setText(location);
        user_address.setText(address);
        user_phone.setText(phone);
        px.setImageBitmap(Fbpic.getRoundedShape(pic));
        edit = (ButtonFloat) v.findViewById(R.id.float_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit edit = new edit();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                fragmentManager.beginTransaction().addToBackStack("details").replace(R.id.mainContent, edit).commit();

            }
        });
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



    public static class Facebook_pic implements Callable<Bitmap>{

        String id;

    public Facebook_pic(String id){
        this.id = id;

    }

        @Override
        public Bitmap call() throws Exception {

            Bitmap bitmap;

            final String nomimg = "https://graph.facebook.com/"+id+"/picture?type=large";
            URL imageURL = new URL(nomimg);
            Log.d("url", nomimg);
            HttpURLConnection connection = (HttpURLConnection) imageURL.openConnection();
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects( true );
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;


        }
    }

}