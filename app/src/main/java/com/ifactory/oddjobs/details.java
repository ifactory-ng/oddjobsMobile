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
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link details.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link details#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class details extends android.support.v4.app.Fragment {


    //private OnFragmentInteractionListener mListener;
    TextView user_email, user_name, user_about, user_phone, user_address, user_location;
    Context context;
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
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment details.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        new load_details(context).execute();
        user_email = (TextView) container.findViewById(R.id.email);
       user_name = (TextView) container.findViewById(R.id.name);
        user_about = (TextView)container.findViewById(R.id.about);

        user_location = (TextView)container.findViewById(R.id.location);

        user_address = (TextView)container.findViewById(R.id.address);

        user_phone = (TextView)container.findViewById(R.id.phone);

        return inflater.inflate(R.layout.fragment_details, container, false);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


private class load_details extends AsyncTask<String, Void, String>{
    Bitmap pic;
        Context c;
    public load_details(Context c){
        this.c = c;

    }
    @Override
    protected String doInBackground(String... params) {

       SharedPreferences editor = c.getSharedPreferences(c.getString(R.string.preference_file_name), c.MODE_PRIVATE);
         id = editor.getString(c.getString(R.string.preference_file_name), "id");
        HttpClient httpclient = new DefaultHttpClient();
       HttpGet httpGet = new HttpGet("localhost:3000/profile/"+id);
        try {
            HttpResponse res = httpclient.execute(httpGet);
             result = EntityUtils.toString(res.getEntity());
        }

        catch (IOException i){
            i.printStackTrace();
        }
        pic = getUserPic(id);
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONArray rs = new JSONArray(s);
            jObj = rs.getJSONObject(0);
            email = jObj.getString("email");
            //name = jObj.getString("name");
            //location = jObj.getString("location");
            //phone = jObj.getString("phone");
            //about = jObj.getString("about");
            //address = jObj.getString("address");
            user_email.setText(email);
            user_name.setText(name);
            user_about.setText(about);
            user_location.setText(location);
            user_address.setText(address);
            user_phone.setText(phone);
            pics.setImageBitmap(pic);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
    public   Bitmap  getUserPic( String  userID) {
        String  imageURL;
        Bitmap bitmap =  null;
        Log.d("Imageview", "Loading Picture");
        imageURL = "http://graph.facebook.com/"+userID+"/picture?type=medium";
        try  {
            bitmap =  BitmapFactory.decodeStream((InputStream) new
                    URL(imageURL).getContent());
        }  catch  ( Exception  e) {
            Log .d("Imageview", "Loading Picture FAILED");
            e.printStackTrace();
        }
        return  bitmap;
    }
}