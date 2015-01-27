package com.ifactory.oddjobs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by smilecs on 1/22/15.
 */
public class product_view extends Fragment {
TextView desc, location, address, name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       super.onCreateView(inflater, container, savedInstanceState);
        String data = getArguments().getString("data");
        try {
            JSONObject Jobject = new JSONObject(data);
            desc = (TextView) getActivity().findViewById(R.id.user_product_desc);
            desc.setText(Jobject.get("description").toString());
            location = (TextView) getActivity().findViewById(R.id.user_product_location);
            location.setText(Jobject.get("location").toString());
            address = (TextView) getActivity().findViewById(R.id.user_product_address);
            address.setText(Jobject.get("address").toString());
            name = (TextView) getActivity().findViewById(R.id.user_product_name);
            name.setText(Jobject.get("product_name").toString());

        }
        catch(JSONException e){
            e.printStackTrace();
        }
            return inflater.inflate(R.layout.user_product, container, false);
    }
}
