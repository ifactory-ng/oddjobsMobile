package com.ifactory.oddjobs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by smilecs on 4/13/15.
 */
public class BookmarkModel {
    String username, skillname, bookmarkid, phone;

    public BookmarkModel(JSONObject data){
        try{
            this.bookmarkid = data.getString("Id");
            this.skillname = data.getString("SkillName");
            this.username = data.getString("Name");
            this.phone = data.getString("Phone");
        }catch(JSONException j){
            j.getMessage();
        }

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSkillname() {
        return skillname;
    }

    public void setSkillname(String skillname) {
        this.skillname = skillname;
    }

    public String getBookmarkid() {
        return bookmarkid;
    }

    public void setBookmarkid(String bookmarkid) {
        this.bookmarkid = bookmarkid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return username;
    }

    public static ArrayList<BookmarkModel> getData(JSONArray jObject) {
        ArrayList<BookmarkModel> model = new ArrayList<BookmarkModel>();
        for(int i = 0; i < jObject.length(); i++){
            try{
                model.add(new BookmarkModel(jObject.getJSONObject(i)));
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }
        return model;
    }
}
