package com.example.androidfinalproject;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;

public class MainActivity extends home {

    // extend BaseActivity to add toolbar functions to Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}