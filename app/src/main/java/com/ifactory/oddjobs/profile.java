package com.ifactory.oddjobs;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;



public class profile extends ActionBarActivity implements skill.Communicator {
private FragmentNavigationDrawer dlDrawer;
    ContentResolver mResolver;
    public static final String ACCOUNT = "com.ifactory.oddjobs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Account newAcct = new Account("oddjobs", "com.ifactory.oddjobs");
        AccountManager mang = (AccountManager) this.getSystemService(ACCOUNT_SERVICE);
        mang.addAccountExplicitly(newAcct, null, null);
        mResolver = getContentResolver();
 //       mResolver.requestSync(newAcct, "com.ifactory.Oddjobs", savedInstanceState);

        mResolver.setSyncAutomatically(newAcct, "com.ifactory.Oddjobs", true);
        setContentView(R.layout.activity_profile);
        if(getIntent().getExtras() != null) {

    getIntent().getStringExtra("query");
    Search_Json p = new Search_Json();
    Bundle args = new Bundle();
    // String data = Jobject.toString();

    args.putString("location", getIntent().getStringExtra("location"));
    args.putString("query", getIntent().getStringExtra("query"));
    p.setArguments(args);
    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    ft.add(R.id.mainContent, p).addToBackStack(null);
    ft.commit();
}
else {


    dlDrawer = (FragmentNavigationDrawer) findViewById(R.id.drawerlayout);
    dlDrawer.setupDrawerConfiguration((ListView) findViewById(R.id.drawerList), R.layout.drawer_item, R.id.mainContent);
    dlDrawer.addNavItem("Search", "Search", Search_Json.class);
    dlDrawer.addNavItem("Profile", "Profile", details.class);
    dlDrawer.addNavItem("Add Skill", "Add Skill", add_product.class);
    dlDrawer.addNavItem("Skill List", "Skill List", skill.class);
    dlDrawer.selectDrawerItem(2);


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


}
