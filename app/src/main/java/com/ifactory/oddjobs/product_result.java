package com.ifactory.oddjobs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product_result, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if(item.getItemId() == R.id.action_search){
            Intent i = new Intent(getApplicationContext(), searchDialog.class);
            startActivity(i);


        }
        return super.onOptionsItemSelected(item);
    }


}
