package com.example.androidfinalproject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity {
    private NavigationView navigationView;
    private Drawable myDrawable;
    ImageView avatar;
    TextView intro;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Adds toolbar to Activity
        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.homedrawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences prefs = getSharedPreferences("profile", Context.MODE_PRIVATE);

        String savedName = prefs.getString("username", " ");
        String savedAvatar = prefs.getString("avatar", " ");

        avatar = findViewById(R.id.ivAvatar);
        intro = findViewById(R.id.tvAvatar);

        String msg = getResources().getString(R.string.intro) + " " + savedName;

        intro.setText(msg);
        avaterControl(savedAvatar);



    }

    public void avaterControl (String savedAvatar){
        if (savedAvatar.equals("alien") || savedAvatar.equals("extraterrestre")) {
            // update image view
            myDrawable = getResources().getDrawable(R.drawable.alien);
        } else if (savedAvatar.equals("orbit") || savedAvatar.equals("orbite")) {
            // update image view
            myDrawable = getResources().getDrawable(R.drawable.orbit);
        } else if (savedAvatar.equals("comet") || savedAvatar.equals("comète")) {
            // update image view
            myDrawable = getResources().getDrawable(R.drawable.comet);
        } else {
            myDrawable = getResources().getDrawable(R.drawable.comet);
        }
        avatar.setImageDrawable(myDrawable);
    }

}