package com.example.androidfinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ImageOfTheDay extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // View and Button Variables.
    TextView tvDate;
    TextView tvURL;
    TextView tvHdURL;
    TextView tvTitle;

    ImageView ivNasa;

    Button btnDate;
    Button btnSave;
    String datePicked;

    NasaImage nasa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_of_the_day);

        // Declare Buttons
        btnDate = findViewById(R.id.btn_date);
        btnSave = findViewById(R.id.btn_save);

        // Declare TextViews
        ivNasa = findViewById(R.id.iv_Nasa);
        tvURL = findViewById(R.id.tv_URL);
        tvHdURL = findViewById(R.id.tv_HdURL);
        tvTitle = findViewById(R.id.tv_Title);

        // Display date picker when clicked.
        btnDate.setOnClickListener( click -> {
            // display date picker fragment
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });

        // Save to favourites.
        btnSave.setOnClickListener( click -> {
            // Implement save to database
        });

        // get name of activity test
        String activity = this.getClass().getSimpleName();
        Log.e("appName", "Activity Name: " + activity);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String displayDate = DateFormat.getDateInstance().format(c.getTime());
        // formated date for JSON
        SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-MM");
        datePicked = format.format(c.getTime());

        NasaPictures req = new NasaPictures();
        req.execute("https://api.nasa.gov/planetary/apod?api_key=DgPLcIlnmN0Cwrzcg3e9NraFaYLIDI68Ysc6Zh3d&date=" + datePicked);

        tvDate = findViewById(R.id.tv_Date);
        tvDate.setText(displayDate);
    }

    private class NasaPictures extends AsyncTask<String, Integer, String> {

        Bitmap imageOfDay;
        String regUrl;
        String hdUrl;
        String title;
        @Override
        protected String doInBackground(String... strings) {

            try {
                // create URL object with api.nasa.gov
                URL url = new URL(strings[0]);

                // open connection with api.nasa.gov
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                InputStream response = connection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while((line = reader.readLine()) != null){
                    sb.append(line + "\n");
                }
                String result = sb.toString();

                // creating JSON object
                JSONObject NasaJson = new JSONObject(result);

                // variables to hold Json data for object creation
                String date = NasaJson.getString("date");
                regUrl = NasaJson.getString("url");
                hdUrl = NasaJson.getString("hdurl");
                title = NasaJson.getString("title");

                // Retrieve Image
                URL nasaUrl = new URL(regUrl);
                HttpURLConnection nasaConnection = (HttpURLConnection) nasaUrl.openConnection();

                imageOfDay = BitmapFactory.decodeStream(nasaConnection.getInputStream());

                Log.d("appName", "image: " + imageOfDay);

                // create NasaImage objects
                nasa = new NasaImage();
                nasa.setDate(date);
                nasa.setTitle(title);
                nasa.setUrl(regUrl);
                nasa.setHdUrl(hdUrl);
                nasa.setImage(imageOfDay);

            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            ivNasa.setImageBitmap(nasa.getImage());
            Log.d("appName", "url " + nasa.getUrl());
            tvURL.setText("URL: " + nasa.getUrl());
            tvHdURL.setText("HD Url: " + nasa.getHdUrl());
            tvTitle.setText(nasa.getTitle());
        }
    }
}