package com.ifactory.oddjobs;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class profile extends FragmentActivity {
private FragmentNavigationDrawer dlDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dlDrawer = (FragmentNavigationDrawer) findViewById(R.id.drawerlayout);
        dlDrawer.setupDrawerConfiguration((ListView) findViewById(R.id.drawerList), R.layout.drawer_item, R.id.mainContent);
        dlDrawer.addNavItem("Profile","Profile", details.class);
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


}
