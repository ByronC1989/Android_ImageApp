package com.example.androidfinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class PictureRoulette extends AppCompatActivity {

    static final String baseUrl = "https://api.nasa.gov/planetary/apod?api_key=DgPLcIlnmN0Cwrzcg3e9NraFaYLIDI68Ysc6Zh3d&date=";
    // View and Button Variables.
    Button btnStart;
    Button btnSave;
    TextView tvURL;
    TextView tvHdURL;
    TextView tvTitle;
    ProgressBar progress;

    String datePicked;
    ImageView ivNasa;

    // store NasaImage
    NasaImage nasa;
    Boolean rouletteStart = false; // determines if roulette runs or not

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_roulette);

        // Declare ProgressBar
        progress = findViewById(R.id.progressBar);

        // Declare Buttons
        btnStart = findViewById(R.id.btn_start);
        btnSave = findViewById(R.id.btn_save);

        // Declare TextViews
        ivNasa = findViewById(R.id.iv_Nasa);
        tvTitle = findViewById(R.id.tv_Title);

        btnStart.setOnClickListener( click -> {
            if(!rouletteStart){
                    rouletteStart = true; // turn
                    btnStart.setText("End Roulette!");
                    PictureRoulette.NasaPictures req = new PictureRoulette.NasaPictures();
                    req.execute(baseUrl);
            } else {
                rouletteStart = false;
                btnStart.setText("Start Roulette!");
            }
        });

        // Save to favourites.
        btnSave.setOnClickListener( click -> {
            if (nasa != null) {
                MyOpener myOpener = new MyOpener(this);
                myOpener.addToDB(nasa.getDate(), nasa.getTitle(), "", nasa.getUrl());
                Toast.makeText(PictureRoulette.this, "Data saved to database", Toast.LENGTH_SHORT).show();
                myOpener.close();
            } else {
                Toast.makeText(PictureRoulette.this, "No data to save", Toast.LENGTH_SHORT).show();
            }
        });

        // get name of activity test
        String activity = this.getClass().getSimpleName();
        Log.e("appName", "Activity Name: " + activity);
    }

    public String randomDate() {
        Random random = new Random();

        int minDay = (int) LocalDate.of(2000,1,1).toEpochDay();
        int maxDay = (int) LocalDate.now().toEpochDay();

        // select a random day bassed on maxDay and minDay
        long randomDay = minDay + random.nextInt(maxDay - minDay);

        // convert random day back to a date.
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        // format date to be added to URL of Nasa API
        datePicked = randomDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Log.d("roulettePic", randomDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        return datePicked;
    }

    private class NasaPictures extends AsyncTask<String, Integer, String> {

        Bitmap imageOfDay;
        String regUrl;
        String hdUrl;
        String title;
        @Override
        protected String doInBackground(String... strings) {

            // find a way to protect against null objects
        while(rouletteStart) {

            try {

                // create URL object with api.nasa.gov
                URL url = new URL(strings[0] + randomDate());

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
                    nasa = new NasaImageBuilder().createNasaImage();
                    nasa.setDate(date);
                    nasa.setTitle(title);
                    nasa.setUrl(regUrl);
                    if (hdUrl != null){
                        nasa.setHdUrl(hdUrl);
                    }
                    nasa.setImage(imageOfDay);

                // update progress bar
                for (int i = 0; i < 100; i++) {
                    try {
                        publishProgress(i);
                        Thread.sleep(50);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }
        }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progress.setProgress(values[0]);
            ivNasa.setImageBitmap(nasa.getImage());
            tvTitle.setText(nasa.getTitle());
        }

//        @Override
//        protected void onPostExecute(String s) {
//            if(nasa != null) {
//                ivNasa.setImageBitmap(nasa.getImage());
//                Log.d("appName", "url " + nasa.getUrl());
//                tvTitle.setText(nasa.getTitle());
//            } else {
//                tvTitle.setText("Oppsies");
//            }
//
//        }
    }
}