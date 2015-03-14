package com.ifactory.oddjobs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by smilecs on 2/7/15.
 */
public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {
    private ArrayList<SkillModel> skill;
    @Override
    public myAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView desc, user_name, locale;
        public RatingBar rating;
        public ViewHolder(View v){
            super(v);
            desc = (TextView) v.findViewById(R.id.description);
            user_name = (TextView) v.findViewById(R.id.user_name);
            locale = (TextView) v.findViewById(R.id.locale);
            rating = (RatingBar) v.findViewById(R.id.ratingBar2);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public myAdapter(ArrayList<SkillModel> skill){

        this.skill = skill;
    }

    @Override
    public void onBindViewHolder(myAdapter.ViewHolder holder, int position) {
    SkillModel model = skill.get(position);
        holder.user_name.setText(model.name);
        holder.desc.setText(model.desc);
        holder.locale.setText(model.location);
        holder.rating.setNumStars(model.rating);


    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
