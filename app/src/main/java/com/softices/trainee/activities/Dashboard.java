package com.softices.trainee.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.softices.trainee.R;

import static com.softices.trainee.sharedpreferences.AppPreferences.clearPreferences;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.menu_profile:
                Intent intent = new Intent(Dashboard.this, ProfileActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_logout:
                clearPreferences(Dashboard.this, false, "");
                Intent intent1 = new Intent(Dashboard.this, SignInActivity.class);
                startActivity(intent1);
                finishAffinity();
                return true;
            case R.id.menu_signUp:
                Intent intent2 = new Intent(Dashboard.this,SignUpActivity.class);
                startActivity(intent2);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
        } else if (id == R.id.nav_user_activity) {
            Intent intent = new Intent(Dashboard.this, UserActivity.class);
            startActivity(intent);
            finishAffinity();
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(Dashboard.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_google_maps) {
            Intent intent = new Intent(Dashboard.this, MapsActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_web_services) {
            Intent intent = new Intent(Dashboard.this, WebServicesActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_contacts) {

        } else if (id == R.id.nav_brodcast_receiver) {

        } else if (id == R.id.nav_logout) {
            clearPreferences(Dashboard.this, false, "");
            Intent intent = new Intent(Dashboard.this, SignInActivity.class);
            startActivity(intent);
            finishAffinity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}