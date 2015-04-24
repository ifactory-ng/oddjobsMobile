package com.ifactory.oddjobs;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.facebook.Session;


public class profile extends ActionBarActivity implements skill.Communicator {
private FragmentNavigationDrawer dlDrawer;
    ContentResolver mResolver;
    String id;
    public static final String ACCOUNT = "com.ifactory.oddjobs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Account newAcct = new Account("oddjobs", "com.ifactory.oddjobs");
        AccountManager mang = (AccountManager) this.getSystemService(ACCOUNT_SERVICE);
        mang.addAccountExplicitly(newAcct, null, null);
        mResolver = getContentResolver();
 //    mResolver.requestSync(newAcct, "com.ifactory.Oddjobs", savedInstanceState);
        mResolver.setSyncAutomatically(newAcct, "com.ifactory.Oddjobs.provider", true);
        setContentView(R.layout.activity_profile);

            SharedPreferences editor = getSharedPreferences(getString(R.string.preference_file_name), MODE_PRIVATE);
        id = editor.getString("_id", "id");
            dlDrawer = (FragmentNavigationDrawer) findViewById(R.id.drawerlayout);
            dlDrawer.setupDrawerConfiguration((ListView) findViewById(R.id.drawerList), R.layout.drawer_item, R.id.mainContent);
            if(id == "id"){
                dlDrawer.addNavItem("Feeds", "Oddjobs", Search_Json.class);
                dlDrawer.addNavItem("Login", "Login", fb_login.class);
            }
            else {

                dlDrawer.addNavItem("Feeds", "Feeds", Search_Json.class);
                dlDrawer.addNavItem("Profile", "Oddjobs", details.class);
                dlDrawer.addNavItem("Skill List", "Oddjobs", skill.class);
                dlDrawer.addNavItem("Bookmarks", "Oddjobs", Bookmark.class);


            }
        if(getIntent().getExtras() != null) {
            dlDrawer.selectDrawerItem(0);
        }else{
            dlDrawer.selectDrawerItem(1);
        }
}




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);

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
    public void itemSelected(String data) {
        product_view p = new product_view();
        Bundle args = new Bundle();
       // String data = Jobject.toString();
        Log.d("data", data);
        args.putString("data", data);
        p.setArguments(args);
 FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.mainContent, p).addToBackStack(null);
        ft.commit();



        //fm.replace();
        //addToBackStack("skill");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
        Intent i = new Intent(getApplicationContext(), profile.class);
        startActivity(i);

    }
}
