package com.ifactory.oddjobs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;

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
    ArrayList<SkillModel> skill;
    Button search;
    TextView search_query;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
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
        View v = inflater.inflate(R.layout.search_result, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);

        mlayoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(mlayoutManager);
        mAdapter = new myAdapter(skill);
        mRecyclerView.setAdapter(mAdapter);
        search_query = (TextView) v.findViewById(R.id.search_query);
        search = (Button) v.findViewById(R.id.search);

        //onClickListner for search.........
        search.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONArray ja = null;
            String q =search_query.getText().toString();
                FutureTask<JSONArray> Jarray = new FutureTask<JSONArray>(new Jobject(routes.GET_SEARCH_RESULTS + q));
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
                mAdapter.notifyDataSetChanged();



            }
        });
        

        return v;
    }

}