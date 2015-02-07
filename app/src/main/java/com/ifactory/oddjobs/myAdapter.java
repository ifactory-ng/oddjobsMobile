package com.ifactory.oddjobs;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

/**
 * Created by smilecs on 2/7/15.
 */
public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {
    private JSONObject mDataset;

    @Override
    public myAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View v){
            super(v);

        }
    }

    public myAdapter(JSONObject mDataset){
        this.mDataset = mDataset;
    }

    @Override
    public void onBindViewHolder(myAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
