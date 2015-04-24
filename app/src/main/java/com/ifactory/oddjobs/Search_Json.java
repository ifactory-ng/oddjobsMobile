package com.ifactory.oddjobs;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by smilecs on 1/28/15.
 */
public class Search_Json  extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;
    private static final String AUTHORITY = "com.ifactory.Oddjobs";
    private static final String PREFIX = "content://" + AUTHORITY + "/";
    TextView emptyview;

    ArrayList<SkillModel> skill;
    Context context;
    //ContentResolver contentResolver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Load_from_provider().execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //contentResolver = getActivity().getApplicationContext().getContentResolver();
        /*
*/
        View v = inflater.inflate(R.layout.search_result, container, false);
        //container.addView(v);

    try {


    mRecyclerView = (RecyclerView) v.findViewById(R.id.cardList);
    emptyview = (TextView) v.findViewById(R.id.empty);
        mlayoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mlayoutManager);


    }catch(NullPointerException npe){
    npe.getMessage();
   // mRecyclerView.setVisibility(View.GONE);
   // emptyview.setVisibility(View.VISIBLE);

}
        //    mRecyclerView.setVisibility(View.GONE);
         //   emptyview.setVisibility(View.VISIBLE);
            //getMessage();


        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


    }
 private class Load_from_provider extends AsyncTask<Void, String, String> {

        @Override
        protected String doInBackground(Void... params) {
            JSONObject ja = new JSONObject();
            JSONArray jsonArray = null;
            String Url;
            String result=" ";

/*            Uri uri = Uri.parse(PREFIX + "feeds");
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            try {
                while (cursor.moveToNext()){
                    ja.put("skillname", cursor.getString(cursor.getColumnIndexOrThrow(DataContract.feeds.NAME)));
                    ja.put("_id", cursor.getString(cursor.getColumnIndexOrThrow(DataContract.feeds.SKILL_ID)));
                    ja.put("rating", cursor.getString(cursor.getColumnIndexOrThrow(DataContract.feeds.RATE_COL)));
                    ja.put("description", cursor.getString(cursor.getColumnIndexOrThrow(DataContract.feeds.DESCRIPTION_COL)));
                    ja.put("address", cursor.getString(cursor.getColumnIndexOrThrow(DataContract.feeds.ADDRESS)));
                    ja.put("location", cursor.getString(cursor.getColumnIndexOrThrow(DataContract.feeds.LOCATION)));
                    jsonArray.put(ja);
            try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet get = new HttpGet(routes.BY_LIMIT);

            HttpResponse response = httpclient.execute(get);
            result = EntityUtils.toString(response.getEntity());

        }
                jsonArray = new JSONArray(ja);
                jsonArray.put(ja);
             catch (IOException i){
                i.getMessage();
            }*/
            try{
                Url = routes.BY_LIMIT;
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet get = new HttpGet(Url);
                Log.d("url", Url);

                HttpResponse response = httpclient.execute(get);
                 result = EntityUtils.toString(response.getEntity());
                Log.d("result", result);


                //JSONArray rs = new JSONArray(result);

//                skill = SkillModel.getData(ja);



            } catch (Throwable i){
                Log.d("search", "search");
                i.getMessage();
            }

            return result;
        }

   @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray jsonFromString = new JSONArray(s);
                skill = SkillModel.getData(jsonFromString);
                mAdapter = new myAdapter(skill);
                mRecyclerView.setAdapter(mAdapter);

                Log.d("skills", skill.toString());
                //mAdapter.notifyDataSetChanged();
       //         mRecyclerView.setVisibility(View.VISIBLE);
         //       emptyview.setVisibility(View.GONE);
            //mAdapter.notifyDataSetChanged();

            } catch (JSONException j) {
                j.getMessage();
            }
        }
    }
}