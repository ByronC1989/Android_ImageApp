package com.example.androidfinalproject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.navigation.NavigationView;

public class ProfileActivity extends BaseActivity {

    private NavigationView navigationView;
    private Drawable myDrawable;
    private String avatarName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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
        SharedPreferences.Editor editor = prefs.edit();

        String savedName = prefs.getString("username", " ");
        String savedAvatar = prefs.getString("avatar", " ");

        Log.d("profile", savedName);
        Log.d("profile", savedAvatar);

        EditText uname = findViewById(R.id.editUsername);
        ImageView avatar = findViewById(R.id.ivAvatar);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        Button btnSave = findViewById(R.id.btnSave);



        // fill in the editText with username
        uname.setText(savedName);
        if (savedAvatar.equals("alien")) {
            // update image view
            myDrawable = getResources().getDrawable(R.drawable.alien);
        } else if (savedAvatar.equals("moon")) {
            // update image view
            myDrawable = getResources().getDrawable(R.drawable.moon);
        } else if (savedAvatar.equals("comet")) {
            // update image view
            myDrawable = getResources().getDrawable(R.drawable.comet);
        } else {
            myDrawable = getResources().getDrawable(R.drawable.comet);
        }
        avatar.setImageDrawable(myDrawable);

        // set avatar
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {

            RadioButton radioButton = group.findViewById(checkedId);

            Log.d("avatar", radioButton.getText() + "");

            if (radioButton.getText().equals("alien")) {
                // update image view
                myDrawable = getResources().getDrawable(R.drawable.alien);
                avatar.setImageDrawable(myDrawable);
                avatarName = "alien";
            } else if (radioButton.getText().equals("moon")) {
                // update image view
                myDrawable = getResources().getDrawable(R.drawable.moon);
                avatar.setImageDrawable(myDrawable);
                avatarName = "moon";
            } else if (radioButton.getText().equals("comet")) {
                // update image view
                myDrawable = getResources().getDrawable(R.drawable.comet);
                avatar.setImageDrawable(myDrawable);
                avatarName = "comet";
            }
        });

        btnSave.setOnClickListener( click -> {

            editor.putString("username", uname.getText().toString());
            // update
            editor.putString("avatar", avatarName);
            editor.commit();

        });

    }
}