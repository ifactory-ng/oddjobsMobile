package com.ifactory.oddjobs;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by smilecs on 2/18/15.
 */
public class product_result extends Activity {
    ImageView productView;
    TextView product_title, product_desc, product_location, product_address, product_phone, product_email;
    Button review, bookmark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       product_title = (TextView)findViewById(R.id.product_title);
        product_desc = (TextView)findViewById(R.id.product_about);
        product_location = (TextView)findViewById(R.id.product_locale);
        product_address = (TextView)findViewById(R.id.product_address);
        

    }

}
