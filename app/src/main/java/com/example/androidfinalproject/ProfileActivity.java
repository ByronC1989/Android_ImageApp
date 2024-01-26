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

    private Drawable myDrawable;
    private String avatarName;
    private ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Adds toolbar to Activity
        Toolbar toolbar = findViewById(R.id.home_toolbar);

        setSupportActionBar(toolbar);


        SharedPreferences prefs = getSharedPreferences("profile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String savedName = prefs.getString("username", " ");
        String savedAvatar = prefs.getString("avatar", " ");

        EditText uname = findViewById(R.id.editUsername);
        avatar = findViewById(R.id.ivAvatar);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        Button btnSave = findViewById(R.id.btnSave);

        // fill in the editText with username
        uname.setText(savedName);
        avaterControl(savedAvatar);


        // set avatar
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {

            RadioButton radioButton = group.findViewById(checkedId);

            if (radioButton.getText().equals("alien") || radioButton.getText().equals("extraterrestre")) {
                // update image view
                myDrawable = getResources().getDrawable(R.drawable.alien);
                avatar.setImageDrawable(myDrawable);
                avatarName = radioButton.getText().toString();
            } else if (radioButton.getText().equals("orbit") || radioButton.getText().equals("orbite")) {
                // update image view
                myDrawable = getResources().getDrawable(R.drawable.orbit);
                avatar.setImageDrawable(myDrawable);
                avatarName = radioButton.getText().toString();
            } else if (radioButton.getText().equals("comet") || radioButton.getText().equals("comète")) {
                // update image view
                myDrawable = getResources().getDrawable(R.drawable.comet);
                avatar.setImageDrawable(myDrawable);
                avatarName = radioButton.getText().toString();
            }
        });

        btnSave.setOnClickListener( click -> {

            editor.putString("username", uname.getText().toString());
            // update
            editor.putString("avatar", avatarName);
            editor.commit();

        });
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