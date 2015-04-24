package com.ifactory.oddjobs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by smilecs on 4/15/15.
 */
public class Bookmark extends ListFragment {
    BookmarkAdapter BA;
    ArrayList<BookmarkModel> BM;
    JSONArray ja;
    String id;
    ListView lv;
    Context c;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         c = getActivity();
  SharedPreferences editor = c.getSharedPreferences(c.getString(R.string.preference_file_name), c.MODE_PRIVATE);
      id = editor.getString("_id", "id");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bookmarklist, container, false);
        lv = (ListView) v.findViewById(android.R.id.list);
        new LoadData().execute();
        return v;
    }

    private class LoadData extends AsyncTask<String, Void, String>{
        String result;
        @Override
        protected String doInBackground(String... params) {
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
            try {
                 ja = new JSONArray(s);
                BM = BookmarkModel.getData(ja);
                BA = new BookmarkAdapter(getActivity().getBaseContext(), BM);
                lv.setAdapter(BA);
            }
            catch (JSONException j){
                j.getMessage();
                Toast tm = Toast.makeText(c, "unable to access data or you have no stored bookmarks", Toast.LENGTH_LONG);
                tm.show();
            }catch (NullPointerException n){
                n.getMessage();
            }
        }
    }
}
