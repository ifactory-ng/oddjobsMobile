package com.ifactory.oddjobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by smile on 11/4/14.
 */
public class pro_adapter extends ArrayAdapter<SkillModel> {
    public pro_adapter(Context context, ArrayList<SkillModel> title){

        super(context, R.layout.fragment_skill_list, title);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SkillModel model = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_skill_list, parent, false);
            }
        TextView skillTitle = (TextView) convertView.findViewById(R.id.listItem);
        skillTitle.setText(model.Title);
        TextView id = (TextView) convertView.findViewById(R.id.listItem2);
        id.setText(model.id);
        return super.getView(position, convertView, parent);
    }


}
