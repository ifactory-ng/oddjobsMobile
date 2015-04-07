package com.ifactory.oddjobs;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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

    ArrayList<SkillModel> skill;
    Context context;
    ContentResolver contentResolver;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        contentResolver = getActivity().getApplicationContext().getContentResolver();
        try{
        getArguments().getString("query");

        JSONArray ja = null;
        FutureTask<JSONArray> Jarray = new FutureTask<JSONArray>(new Jobject(routes.BY_LIMIT));
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(Jarray);
        try {
            ja = Jarray.get();
        } catch (InterruptedException i) {
            i.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        es.shutdown();
            skill = SkillModel.getData(ja);

        } catch (Throwable i){
            Log.d("search", "search");
            new Load_from_provider().execute();
            i.getMessage();
        }
        View v = inflater.inflate(R.layout.search_result, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.cardList);
        TextView emptyview = (TextView) v.findViewById(R.id.empty);
        try{
        if(!skill.isEmpty()){


         mRecyclerView.setVisibility(View.VISIBLE);
            emptyview.setVisibility(View.GONE);
            mRecyclerView.setHasFixedSize(true);

            mlayoutManager = new LinearLayoutManager(v.getContext());
            mRecyclerView.setLayoutManager(mlayoutManager);
            mAdapter = new myAdapter(skill);
            mRecyclerView.setAdapter(mAdapter);
        }
        }
        catch (Throwable t){
            mRecyclerView.setVisibility(View.GONE);
            emptyview.setVisibility(View.VISIBLE);
            t.getMessage();
        }

        return v;
    }

    private class Load_from_provider extends AsyncTask<Void, String, String> {

        @Override
        protected String doInBackground(Void... params) {
            JSONObject ja = new JSONObject();
            JSONArray jsonArray = null;



            Uri uri = Uri.parse(PREFIX + "feeds");
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
                }
                //jsonArray = new JSONArray(ja);
                //jsonArray.put(ja);

            } catch (JSONException j) {
                j.printStackTrace();
            }

            return jsonArray.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray jsonFromString = new JSONArray(s);
                skill = SkillModel.getData(jsonFromString);
            } catch (JSONException j) {
                j.getMessage();
            }
        }
    }
}