package com.example.androidfinalproject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
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

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
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

/**
 *  This class allows the user to find random dates
 *  and displays the image of that day from NASA the user can then
 *  save the image.
 *
 * @author Byron, Alycia
 */

public class PictureRoulette extends BaseActivity {

    static final String baseUrl = "https://api.nasa.gov/planetary/apod?api_key=DgPLcIlnmN0Cwrzcg3e9NraFaYLIDI68Ysc6Zh3d&date=";
    // View and Button Variables.
    private Button btnStart;
    private Button btnSave;
    private TextView tvTitle;
    private ProgressBar progress;
    private String datePicked;
    private ImageView ivNasa;

    // store NasaImage
    private NasaImage nasa;
    private Boolean rouletteStart = false; // determines if roulette runs or not
    private Bitmap imageOfDay;
    private Bitmap image;
    private String regUrl;
    private String hdUrl;
    private String title;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_roulette);

        // Adds toolbar to Activity
        Toolbar toolbar = findViewById(R.id.home_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.picRoulette));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

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
                    btnStart.setText(getResources().getString(R.string.btn_end));
                    PictureRoulette.NasaPictures req = new PictureRoulette.NasaPictures();
                    req.execute(baseUrl);
            } else {
                rouletteStart = false;
                btnStart.setText(getResources().getString(R.string.btn_roulette));
            }
        });

        // Save to favourites.
        btnSave.setOnClickListener( click -> {
            if (nasa != null) {
                MyOpener myOpener = new MyOpener(this);
                myOpener.addToDB(nasa.getDate(), nasa.getTitle(), saveToFile(nasa), nasa.getUrl(), nasa.getHdUrl());
                Snackbar.make(btnSave, getResources().getString(R.string.save_data), Snackbar.LENGTH_SHORT).show();
                // add undo
                myOpener.close();
            } else {
                Toast.makeText(PictureRoulette.this, getResources().getString(R.string.no_data), Toast.LENGTH_SHORT).show();
            }
        });

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

        return datePicked;
    }

    private String saveToFile(NasaImage nasa) {
        String fileName = "";

        if (nasa != null) {
            try {
                fileName = "nasa_image_" + nasa.getDate() + ".jpeg";
                image = nasa.getImage();
                FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                nasa.setFilePath(fileName);
                outputStream.flush();
                outputStream.close();

                return fileName;

            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        return fileName;
    }

    private class NasaPictures extends AsyncTask<String, Integer, String> {

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

                    // create NasaImage objects
                    nasa = new NasaImageBuilder().createNasaImage();
                    nasa.setDate(date);
                    nasa.setTitle(title);
                    nasa.setUrl(regUrl);
                    if (hdUrl != null){
                        nasa.setHdUrl(hdUrl);
                    } else {
                        hdUrl = "None";
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
    }
}