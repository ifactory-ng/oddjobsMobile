package com.ifactory.oddjobs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by smilecs on 6/19/15.
 */
public class fb extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = new Intent(getActivity().getApplicationContext(), fb_login.class);
        startActivity(i);
    }
}
