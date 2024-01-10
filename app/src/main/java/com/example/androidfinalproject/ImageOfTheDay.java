package com.example.androidfinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class ImageOfTheDay extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // View and Button Variables.
    TextView tvDate;
    TextView tvURL;
    TextView tvHdURL;

    Button btnDate;
    Button btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_of_the_day);

        // Declare Buttons
        btnDate = findViewById(R.id.btn_date);
        btnSave = findViewById(R.id.btn_save);

        btnDate.setOnClickListener( click -> {
            // display date picker fragment
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });

        // get name of activity test
//        String activity = this.getClass().getSimpleName();
//        Log.e("appName", "Activity Name: " + activity);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance().format(c.getTime());

        tvDate = findViewById(R.id.tv_Date);
        tvDate.setText(currentDateString);
    }
}