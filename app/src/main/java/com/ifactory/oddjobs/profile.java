package com.ifactory.oddjobs;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;


public class profile extends FragmentActivity implements skill.Communicator {
private FragmentNavigationDrawer dlDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Facebook fb = new Facebook(fb_login.APP_ID);

        setContentView(R.layout.activity_profile);
        dlDrawer = (FragmentNavigationDrawer) findViewById(R.id.drawerlayout);
        dlDrawer.setupDrawerConfiguration((ListView) findViewById(R.id.drawerList), R.layout.drawer_item, R.id.mainContent);
        dlDrawer.addNavItem("Profile","Profile", details.class);
        dlDrawer.addNavItem("Add Product", "Add Product", add_product.class);
        dlDrawer.addNavItem("Edit","Edit Profile", edit.class);
        dlDrawer.addNavItem("Skill List","Skill List", skill.class);
        if(savedInstanceState == null){
            dlDrawer.selectDrawerItem(0);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if(dlDrawer.getDrawerToggle().onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        dlDrawer.getDrawerToggle().syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    dlDrawer.getDrawerToggle().onConfigurationChanged(newConfig);
    }

    @Override
    public void itemSelected(JSONObject Jobject) {
        product_view p = new product_view();
        Bundle args = new Bundle();
        String data = Jobject.toString();
        args.putString("data", data);
        p.setArguments(args);
 FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.layout.activity_profile, p).addToBackStack(null);
        ft.commit();



        //fm.replace();
        //addToBackStack("skill");
    }
}
