package com.ifactory.oddjobs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by smile on 11/4/14.
 */
public class SkillModel {
    public String Title;
    public String id;
    String location;
    String name;
    int rating;
    String desc;
    JSONObject jb;
    public SkillModel(JSONObject object) {
        try
        {
  //          this.name = object.getString("name");
        this.Title = object.getString("SkillName");
            this.id = object.getString("_id");
            this.desc = object.getString("Description");
            this.location = object.getString("Location");
         this.rating = object.getInt("Rating");
//            this.jb = object;
    }
    catch(JSONException e){
        e.printStackTrace();
    }

}
    public String toString(){
        return name;
    }

/*public int returnId(){
    int i = Integer.parseInt(id);
    return i;
}*/
    public JSONObject passJson(){

        return jb;
    }

    public static ArrayList<SkillModel> getData(JSONArray jObject) {
        ArrayList<SkillModel> model = new ArrayList<SkillModel>();
        for(int i = 0; i < jObject.length(); i++){
            try{
                model.add(new SkillModel(jObject.getJSONObject(i)));
                }
            catch(JSONException e){
                e.printStackTrace();
            }
        }
        return model;
    }
}
