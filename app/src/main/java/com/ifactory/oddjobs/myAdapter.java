package com.ifactory.oddjobs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
   // private final View.OnClickListener mOnClickListener = new View.OnClickListener();

    private ArrayList<SkillModel> skill;
   static String id;
    @Override
    public myAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search, parent, false);
        //v.setOnClickListener();
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView desc, user_name, locale, addr, skills, rating;
        Context c;

        public ViewHolder(View v){
            super(v);
c = v.getContext();
            desc = (TextView) v.findViewById(R.id.description);
            skills = (TextView) v.findViewById(R.id.skill_name);
            user_name = (TextView) v.findViewById(R.id.skillusername);
            locale = (TextView) v.findViewById(R.id.skill_location);
          //  rating = (TextView) v.findViewById(R.id.rBar2);
            addr = (TextView) v.findViewById(R.id.skill_address);

    v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("recycler", desc.getText().toString());
                    SkillModel md = (SkillModel) v.getTag();
                    Log.d("loggging", md.toString());
                    //Log.d("id", md.id.toString());
                   /* Intent i = new Intent(c, product_result.class);
                    i.putExtra("id", id);
                    c.startActivity(i);
*/



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
        holder.skills.setText(model.Title);
        holder.desc.setText(model.desc);
        holder.addr.setText(model.addr);
        holder.user_name.setText(model.username);
        holder.locale.setText(model.location);
        id = model.id;
        //holder.rating.setText(model.rating);


    }

    @Override
    public int getItemCount() {

        try{
            if(skill.isEmpty()){
                return 0;
            }
        }catch (NullPointerException npe){
            npe.getMessage();
            return 0;
        }

        return skill.size();
    }
}
