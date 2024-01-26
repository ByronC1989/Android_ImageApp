package com.example.androidfinalproject;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

/**
 *  This class displayed a list of all the
 *  saved images that the user selected
 *
 * @author Byron, Alycia
 */

public class favourite_date_list extends BaseActivity {
    private List<NasaImage> SavedDateList;
    private MyListAdapter NasaImage;
    private MyOpener myOpener;

    // define bundle tags
    private static final String NASA_TITLE = "title";
    private static final String NASA_DATE = "date";
    private static final String NASA_HDURL = "hdUrl";
    private static final String NASA_URL = "url";
    private static final String NASA_FILEPATH = "filePath";


    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_date);

        // Adds toolbar to Activity
        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.favourite));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        // Initialize database opener
        myOpener = new MyOpener(this);

        // Load saved dates from the database
        SavedDateList = myOpener.loadData();
        // Adapter for the ListView
        NasaImage = new MyListAdapter(SavedDateList);

        // Set up ListView
        ListView savedDateListView = findViewById(R.id.list_view);
        savedDateListView.setAdapter(NasaImage);

        // Receive the date information from the intent
//        Intent intent = getIntent();
//        if (intent != null && intent.hasExtra("date")) {
//            String date = intent.getStringExtra("date");
//            String title = intent.getStringExtra("title");
//            String hdUrl = intent.getStringExtra("hdUrl");
//            String filePath = intent.getStringExtra("filePath");
//            String url = intent.getStringExtra("url");
//
//            // Use date as ID
//            NasaImage savedDate = new NasaImageBuilder().setDate(date).setUrl(url).setHdUrl(hdUrl).setTitle(title).setFilePath(filePath).createNasaImage();
//            SavedDateList.add(savedDate);
//
//            // Add the new date to the database
//            myOpener.addToDB(date,"", "", getNasaPictureUrl(date),"");
//
//            // Update the ListView
//            NasaImage.notifyDataSetChanged();
//        }


        // Set long click listener for items in the ListView
        savedDateListView.setOnItemLongClickListener((parent, view, position, id) -> {
            // Display a confirmation for deleting a date
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(getResources().getString(R.string.delete))
                    .setMessage(getResources().getString(R.string.row_num)+ " " + (position + 1))
                    .setPositiveButton(getResources().getString(R.string.btn_yes), (click, arg) -> {
                        String Date = SavedDateList.get(position).getDate();

                        // Delete the date from the database
                        myOpener.deleteFromDB(Date);

                        // Remove the date from the list and update the ListView
                        SavedDateList.remove(position);
                        NasaImage.notifyDataSetChanged();
                    })
                    .setNegativeButton(getResources().getString(R.string.btn_no), (click, arg) -> {
                    })
                    .create().show();
            return true;
        });

        savedDateListView.setOnItemClickListener((parent, view, position, id) -> {
            NasaImage nasa = (NasaImage) savedDateListView.getAdapter().getItem(position);

                // create bundle
                Bundle dataToPass = new Bundle();

                // assigning values to be passed
                dataToPass.putString(NASA_TITLE, nasa.getTitle());
                dataToPass.putString(NASA_DATE, nasa.getDate());
                dataToPass.putString(NASA_HDURL, nasa.getHdUrl());
                dataToPass.putString(NASA_URL, nasa.getUrl());
                dataToPass.putString(NASA_FILEPATH, nasa.getFilePath());

                // send data to fragment activity
                Intent nextActivity = new Intent(favourite_date_list.this, FrameActivity.class);
                nextActivity.putExtras(dataToPass);
                startActivity(nextActivity);
        });
    }

    // Construct the URL for NASA picture based on the date
//    public static String getNasaPictureUrl(String date) {
//        return "https://api.nasa.gov/planetary/apod?api_key=DgPLcIlnmN0Cwrzcg3e9NraFaYLIDI68Ysc6Zh3d&date=" + date;
//    }

    // Adapter class for the ListView
    class MyListAdapter extends BaseAdapter {
        private List<NasaImage> savedDateList;

        public MyListAdapter(List<NasaImage> savedDates) {
            super();
            this.savedDateList = savedDates;
        }

        public int getCount() {
            return savedDateList.size();
        }

        public Object getItem(int position) {
            return savedDateList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View old, ViewGroup parent) {
            View newView = old;
            LayoutInflater inflater = getLayoutInflater();

            // Inflate layout
            if (newView == null) {
                newView = inflater.inflate(R.layout.nasa_image_item, parent, false);
            }

            // Reference to TextView in the layout
            TextView tView = newView.findViewById(R.id.list_content);

            // Get current saved date object
            NasaImage savedDate = (NasaImage) getItem(position);

            // Set date text in the TextView
            tView.setText(savedDate.getDate());

            return newView;
        }
    }
}