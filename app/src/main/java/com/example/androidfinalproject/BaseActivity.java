package com.example.androidfinalproject;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar_main);

        // Get Toolbar Layout
        Toolbar tBar = findViewById(R.id.toolbar);

        // load toolbar
        setSupportActionBar(tBar);

        // get name of activity test
        String activity = this.getClass().getSimpleName();
        Log.e("appName", "Activity Name: " + activity);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}