package com.example.androidfinalproject;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private String msg;

    String activity = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar_layout);

        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        DrawerLayout drawerLayout = findViewById(R.id.homedrawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences prefs = getSharedPreferences("profile", Context.MODE_PRIVATE);
        String savedName = prefs.getString("username", " ");

        msg = getResources().getString(R.string.intro) + " " + savedName;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.home) {
            // brings you to main page
            if (!activity.equals("MainActivity")) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }

        } else if (item.getItemId() == R.id.help) {
            showHelpDialog();
        }

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String message = null;
        //on click
        if (item.getItemId() == R.id.home) {
            // brings you to main page
            if (!activity.equals("MainActivity")) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        } else if (item.getItemId() == R.id.picOfTheDay) {
            // brings you to picture of the day
            if (!activity.equals("ImageOfTheDay")) {
                Intent titleIntent = new Intent(this, ImageOfTheDay.class);
                startActivity(titleIntent);
            }
        } else if (item.getItemId() == R.id.picRoulette) {
            // brings you to picture roulette
            if (!activity.equals("PictureRoulette")) {
                Intent rouletteIntent = new Intent(this, PictureRoulette.class);
                startActivity(rouletteIntent);
            }
        } else if (item.getItemId() == R.id.profile) {
            // brings you to profile
            if (!activity.equals("ProfileActivity")) {
                Intent profileIntent = new Intent(this, ProfileActivity.class);
                startActivity(profileIntent);
            }
        } else if (item.getItemId() == R.id.favouritePic) {
            // brings you to profile
            if (!activity.equals("favourite_date_list")) {
                Intent favouriteIntent = new Intent(this, favourite_date_list.class);
                startActivity(favouriteIntent);
            }
        } else if (item.getItemId() == R.id.exit) {
            // exit application
            finishAffinity();
        }

        Toast.makeText(this, "You selected: " + item, Toast.LENGTH_SHORT).show();

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    private void showHelpDialog() {

        String helpDisplay = "";

        if (activity.equals("MainActivity")) {

            helpDisplay = msg
                    + "\n\n" + getResources().getString(R.string.helpheading) + "\n\n"
                    + getResources().getString(R.string.help1) + "\n\n"
                    + getResources().getString(R.string.help2) + "\n\n"
                    + getResources().getString(R.string.help3) + "\n\n"
                    + getResources().getString(R.string.help4);

        } else if (activity.equals("ImageOfTheDay")) {
            helpDisplay = msg + "\n\n" + getResources().getString(R.string.picOfDayHelp);
        } else if (activity.equals("PictureRoulette")) {
            helpDisplay = msg + "\n\n" + getResources().getString(R.string.picRouletteHelp);
        } else if (activity.equals("favourite_date_list")) {
            helpDisplay = msg + "\n\n" + getResources().getString(R.string.favouritesHelp);
        } else if (activity.equals("ProfileActivity")) {
            helpDisplay = msg + "\n\n" + getResources().getString(R.string.profileHelp);
        }


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.helpTitle));
            builder.setMessage(helpDisplay);

            builder.setPositiveButton("OK", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
