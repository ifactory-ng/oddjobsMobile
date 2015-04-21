package com.ifactory.oddjobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by smilecs on 4/14/15.
 */
public class BookmarkAdapter extends ArrayAdapter <BookmarkModel>{
    TextView skillname, phone;
    public BookmarkAdapter(Context context, ArrayList<BookmarkModel> bookmarks){
        super(context, R.layout.bookmarkview, R.id.bookname, bookmarks);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookmarkModel model = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bookmarkview, parent, false);
        }

        TextView username = (TextView) convertView.findViewById(R.id.bookname);
        username.setText(model.getUsername());
        skillname = (TextView) convertView.findViewById(R.id.bookskill);
        skillname.setText(model.getSkillname());
        phone = (TextView) convertView.findViewById(R.id.bookphone);
        phone.setText(model.getPhone());
        return super.getView(position, convertView, parent);
    }

}
