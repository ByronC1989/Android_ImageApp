package com.example.androidfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences prefs = getSharedPreferences("profile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String savedName = prefs.getString("username", " ");
        String avatarName = "Astronaut";
        String savedAvatar = prefs.getString("avatar", " ");

        EditText uname = findViewById(R.id.editUsername);
        ImageView avatar = findViewById(R.id.ivAvatar);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        Button btnSave = findViewById(R.id.btnSave);

        // fill in the editText with username
        uname.setText(savedName);

        // set avatar
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {

            RadioButton radioButton = group.findViewById(checkedId);

            Log.d("avatar", radioButton.getText() + "");

            if(radioButton.getText() == "RadioButton1") {
                // update image view
                // avatar.setImageDrawable();
            } else if(radioButton.getText() == "RadioButton2") {
                // update image view
                // avatar.setImageDrawable();
            } else {
                // update image view
                // avatar.setImageDrawable();
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