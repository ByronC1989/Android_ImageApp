package com.example.androidfinalproject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity {
    // extend BaseActivity to add toolbar functions to Activity
    private NavigationView navigationView;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        Button helpButton = findViewById(R.id.btn_help);
        helpButton.setOnClickListener(v -> showHelpDialog());
        DrawerLayout drawerLayout = findViewById(R.id.homedrawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        // get name of activity test
        String activity = this.getClass().getSimpleName();
        Log.e("appName", "Activity Name: " + activity);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.homepage_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;

        if (item.getItemId() == R.id.home) {
            message = "the title";
        } else if (item.getItemId() == R.id.profile) {
            message = "the title!";
        }

        DrawerLayout drawerLayout = findViewById(R.id.homedrawer);
        drawerLayout.closeDrawer(GravityCompat.START);

        if (message != null) {
            Toast.makeText(this, "You clicked on: " + message, Toast.LENGTH_LONG).show();
        }
        return true;
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String message = null;
        //on click
        if (item.getItemId() == R.id.home) {
            message = "Home";
            // brings you to main page
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            // brings you to picture of the day
        } else if (item.getItemId() == R.id.picOfTheDay) {
            message = "Picture of the Day";
            Intent titleIntent = new Intent(this, ImageOfTheDay.class);
            startActivity(titleIntent);
        } else if (item.getItemId() == R.id.picRoulette) {
            // brings you to picture roulette
            message = "Picture Roulette";
            Intent rouletteIntent = new Intent(this, PictureRoulette.class);
            startActivity(rouletteIntent);
        } else if (item.getItemId() == R.id.profile) {
            // brings you to profile
            message = "Profile";
            Intent profileIntent = new Intent(this, ProfileActivity.class);
            startActivity(profileIntent);
        } else if (item.getItemId() == R.id.favouritePic) {
            // brings you to profile
            message = "favouritePic";
            Intent favouriteIntent = new Intent(this, favourite_date_list.class);
            startActivity(favouriteIntent);
        } else if (item.getItemId() == R.id.exit) {
            // exit application
            message = "Exit";
            finishAffinity();
        }

        DrawerLayout drawerLayout = findViewById(R.id.homedrawer);
        drawerLayout.closeDrawer(GravityCompat.START);

        if (message != null) {
            Toast.makeText(this, "You have clicked: " + message, Toast.LENGTH_LONG).show();
        }

        return true;
    }
    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Help");
        builder.setMessage("Explanation for each program:\n\n"
                + "1. Image of the Day: Enter a date to view or save the NASA image of the day!\n\n"
                + "2. Picture Roulette: Picture roulette selects a random date to view!\n\n"
                + "3. Favourites: View the previously selected favourites!");

        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}