package com.ifactory.oddjobs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by smilecs on 3/13/15.
 */
public class searchDialog extends Activity {
    TextView location, search_query;
    Button search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_dialog);
        location = (TextView) findViewById(R.id.search_location);
        search_query = (TextView) findViewById(R.id.search_query);
        search = (Button) findViewById(R.id.send);
        search.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("location", location.getText().toString());
                intent.putExtra("query", search_query.getText().toString());

            }
        });

    }

}
