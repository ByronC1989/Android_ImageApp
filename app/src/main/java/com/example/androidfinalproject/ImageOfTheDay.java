package com.example.androidfinalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ImageOfTheDay extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    // View and Button Variables.
    private TextView tvDate;
    private TextView tvTitle;
    private ImageView ivNasa;

    private Button btnDate;
    private Button btnSave;
    private Button btnHD;
    private String datePicked;

    private NasaImage nasa;
    private Bitmap image;
    private Bitmap imageOfDay;
    private String regUrl;
    private String hdUrl;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_of_the_day);

        // Adds toolbar to Activity
        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.picOfDay));


        // Declare Buttons
        btnDate = findViewById(R.id.btn_date);
        btnSave = findViewById(R.id.btn_save);
        btnHD = findViewById(R.id.btnHD);

        // Declare TextViews
        ivNasa = findViewById(R.id.iv_Nasa);
        tvTitle = findViewById(R.id.tv_Title);

        // Display date picker when clicked.
        btnDate.setOnClickListener( click -> {
            // display date picker fragment
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });

        // Save to favourites.
        btnSave.setOnClickListener(click -> {
            if (nasa != null) {

                MyOpener myOpener = new MyOpener(this);
                myOpener.addToDB(nasa.getDate(), nasa.getTitle(), saveToFile(nasa), nasa.getUrl(), nasa.getHdUrl());
                Snackbar.make(btnSave, getResources().getString(R.string.save_data), Snackbar.LENGTH_SHORT).show();
                myOpener.close();
            } else {

                Toast.makeText(ImageOfTheDay.this, getResources().getString(R.string.no_data), Toast.LENGTH_SHORT).show();
            }
        });

        // open HD link in browser
        btnHD.setOnClickListener(click -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(getResources().getString(R.string.dialog_title))

                    .setMessage(getResources().getString(R.string.dialog_msg))

                    .setPositiveButton(getResources().getString(R.string.btn_yes), ( clicked, arg) -> {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(nasa.getHdUrl()));
                        startActivity(browserIntent);
                    })

                    .setNegativeButton(getResources().getString(R.string.btn_no), ( clicked, arg) -> { })

                    .create().show();
        });

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
        } else {
            Toast.makeText(ImageOfTheDay.this, getResources().getString(R.string.no_data), Toast.LENGTH_SHORT).show();
        }
        return fileName;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String displayDate = DateFormat.getDateInstance().format(c.getTime());
        // formated date for JSON
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        datePicked = format.format(c.getTime());

        NasaPictures req = new NasaPictures();
        req.execute("https://api.nasa.gov/planetary/apod?api_key=DgPLcIlnmN0Cwrzcg3e9NraFaYLIDI68Ysc6Zh3d&date=" + datePicked);

        tvDate = findViewById(R.id.tv_Date);
        tvDate.setText(displayDate);
    }

    private class NasaPictures extends AsyncTask<String, Integer, String> {

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

            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            ivNasa.setImageBitmap(nasa.getImage());
            tvTitle.setText(nasa.getTitle());
        }
    }
}