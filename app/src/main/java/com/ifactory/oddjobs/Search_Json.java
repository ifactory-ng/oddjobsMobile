package com.ifactory.oddjobs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by smilecs on 1/28/15.
 */
public class Search_Json  extends Fragment{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       //super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.search_result, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);

        mlayoutManager = new LinearLayoutManager(getActivity());
        //mAdapter = new myAdapter(dataset);
        return v;

    }
}
